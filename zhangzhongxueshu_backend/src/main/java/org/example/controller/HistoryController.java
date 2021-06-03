package org.example.controller;

import org.example.error.BusinessException;
import org.example.error.EmBusinessError;
import org.example.response.CommonReturnType;
import org.example.service.CommentService;
import org.example.service.HistoryService;
import org.example.service.model.CommentModel;
import org.example.service.model.HistoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("history")
@RequestMapping("/history")
public class HistoryController extends BaseController {

    @Autowired
    private HistoryService historyService;


    //发送浏览记录
    @RequestMapping(value = "/send")
    @ResponseBody
    public CommonReturnType sendComment(@RequestParam(name="articleid") String articleid,
                                        @RequestParam(name="time") String time,
                                        @RequestParam(name="email") String email) throws BusinessException {


        HistoryModel historyModel=new HistoryModel();
        historyModel.setArticleid(articleid);
        historyModel.setEmail(email);
        historyModel.setTime(time);
        historyService.insertHistory(historyModel);
        return CommonReturnType.create("成功");
    }

}
