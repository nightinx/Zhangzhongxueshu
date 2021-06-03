package org.example.controller;

import org.example.error.BusinessException;
import org.example.error.EmBusinessError;
import org.example.response.CommonReturnType;
import org.example.service.ArticleService;
import org.example.service.CommentService;
import org.example.service.UserService;
import org.example.service.model.ArticleModel;
import org.example.service.model.CommentModel;
import org.example.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Locale;

@Controller("comment")
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;


    //根据articleid获取信息
    @RequestMapping(value = "/get")
    @ResponseBody
    public CommonReturnType getComment(@RequestParam(name="articleid") String articleid) throws BusinessException {

        List<CommentModel> commentModelList=commentService.getCommentByArticleid(articleid);

        if(commentModelList.size()==0){
            throw new BusinessException(EmBusinessError.COMMNENT_NOT_EXIST);
        }

        return CommonReturnType.create(commentModelList);
    }

    //插入评论
    @RequestMapping(value = "/send")
    @ResponseBody
    public CommonReturnType sendComment(@RequestParam(name="articleid") String articleid,
                                        @RequestParam(name="time") String time,
                                        @RequestParam(name="email") String email,
                                        @RequestParam(name="content") String content) throws BusinessException {


        CommentModel commentModel=new CommentModel();
        commentModel.setArticleid(articleid);
        commentModel.setContent(content);
        commentModel.setEmail(email);
        commentModel.setTime(time);
        commentService.insertComment(commentModel);
        return CommonReturnType.create("评论成功");
    }









}

