package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Partner;
import com.example.study.model.newtwork.Header;
import com.example.study.model.newtwork.request.PartnerApiRequest;
import com.example.study.model.newtwork.response.PartnerApiResponse;
import com.example.study.service.PartnerApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/partner")
public class PartnerApiController extends CrudController<PartnerApiRequest, PartnerApiResponse, Partner> {

}
