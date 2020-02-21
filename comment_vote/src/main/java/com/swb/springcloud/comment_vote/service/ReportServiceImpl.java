package com.swb.springcloud.comment_vote.service;

import com.swb.springcloud.comment_vote.exception.IllegalParamsException;
import com.swb.springcloud.comment_vote.feign.Client.ESFeignClient;
import com.swb.springcloud.comment_vote.pojo.Comment;
import com.swb.springcloud.comment_vote.pojo.Report;
import com.swb.springcloud.comment_vote.repository.CommentRepository;
import com.swb.springcloud.comment_vote.repository.ReportRepository;
import com.swb.springcloud.comment_vote.thread.DeleteUser;
import com.swb.springcloud.comment_vote.thread.ESTongXi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ESFeignClient esFeignClient;

    @Override
    public Report save(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Page<Report> findReportByType(String type,int pageIndex) {
        Pageable pageable= PageRequest.of(pageIndex,10);
        return reportRepository.findAllByTypeIsAndHandleOrderByCreateTimeDesc(type,false,pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Report edit(Long id, boolean result) {
        Report report=reportRepository.findById(id).orElse(null);
        if(report==null){
            throw new IllegalParamsException(IllegalParamsException.Type.REPORT_NOTHAVE,"不存在的举报");
        }
        if(report.isHandle()){
            throw new IllegalParamsException(IllegalParamsException.Type.REPORT_PROCESSED,"举报已处理");
        }
        if (result) {
            String type=report.getType();
            if(type.equals("user")){
                DeleteUser deleteUser=new DeleteUser(reportRepository,report.getReportedId(),esFeignClient);
                Thread thread=new Thread(deleteUser);
                thread.start();
            } else if(type.equals("comment")) {
                Comment comment=commentRepository.findById(report.getReportedId()).orElse(null);
                if(comment!=null){
                    Long i=comment.getFlowerId();
                    commentRepository.subFlowerCommentSize(i);
                    ESTongXi esTongXi=new ESTongXi(esFeignClient,i,"sub","comment");
                    Thread thread=new Thread(esTongXi);
                    thread.start();
                    commentRepository.deleteById(report.getReportedId());
                }
            } else if(type.equals("flower")){
                reportRepository.subUserShareNumberByFlowerId(report.getReportedId());
                reportRepository.deleteFlowerByFlowerId(report.getReportedId());
                esFeignClient.deleteEsFlowerById(report.getReportedId());
            } else{
                reportRepository.deleteArticleByReportedId(report.getReportedId());
            }
        }
        report.setHandle(true);
        report.setResult(result);
        report=reportRepository.save(report);
        return report;
    }

    @Override
    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}
