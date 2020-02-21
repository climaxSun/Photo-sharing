package com.swb.springcloud.comment_vote.service;

import com.swb.springcloud.comment_vote.pojo.Report;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReportService {

    Report save(Report report);

    Page<Report> findReportByType(String type,int pageIndex);

    Report edit(Long id,boolean result);

    void deleteReport(Long reportId);
}
