package com.swb.springcloud.comment_vote.thread;

import com.swb.springcloud.comment_vote.feign.Client.ESFeignClient;
import com.swb.springcloud.comment_vote.repository.ReportRepository;
import org.springframework.transaction.annotation.Transactional;

public class DeleteUser implements Runnable {

    private ReportRepository reportRepository;

    private ESFeignClient esFeignClient;

    private Long id;

    public DeleteUser(ReportRepository reportRepository,Long id,ESFeignClient esFeignClient) {
        this.reportRepository = reportRepository;
        this.id=id;
        this.esFeignClient=esFeignClient;
    }

    @Override
    @Transactional
    public void run() {
        System.out.println("deleteUser.start");
        reportRepository.deleteFlowerByUserId(id);
        esFeignClient.deleteEsFlowerByUserId(id);
        reportRepository.deleteUser(id);
        reportRepository.deleteAllByReportedId(id);
        System.out.println("deleteUser.stop");
    }

}
