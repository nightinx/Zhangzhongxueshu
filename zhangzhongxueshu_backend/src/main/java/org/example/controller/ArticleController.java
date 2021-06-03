package org.example.controller;

import org.example.controller.viewobject.UserVO;
import org.example.error.BusinessException;
import org.example.error.EmBusinessError;
import org.example.response.CommonReturnType;
import org.example.service.ArticleService;
import org.example.service.UserService;
import org.example.service.model.ArticleModel;
import org.example.service.model.UserModel;
import org.example.sessionutils.MySessionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wkr on 2021/5/16
 */
@Controller("article")
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    //根据title获取文章信息
    @RequestMapping(value = "/search")
    @ResponseBody
    public CommonReturnType getArticle(@RequestParam(name="title") String title) throws BusinessException {
        //调用service服务获取对应email的用户对象并返回给前端

        List<ArticleModel> articleModelList=articleService.getArticleByTitle(title);
        //若获取的对应用户信息不存在
        if(articleModelList.size()==0){
            throw new BusinessException(EmBusinessError.ARTICLE_NOT_EXIST);
        }

        return CommonReturnType.create(articleModelList);
    }


    //推荐文章
    @RequestMapping(value = "/recommend")
    @ResponseBody
    public CommonReturnType recommendArticle(@RequestParam(name="email") String email) throws BusinessException {
        //调用service服务获取对应email的用户对象并返回给前端

        UserModel userModel=userService.getUserByEmail(email);
        //若获取的对应用户信息不存在
        if(userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        //ICCV,ECCV,CVPR
        int[] typenum= {1, 1, 1};

        switch (userModel.getInterest_1().toUpperCase(Locale.ROOT)){
            case "ICCV":
                typenum[0]+=24;
                break;
            case "ECCV":
                typenum[1]+=24;
                break;
            case "CVPR":
                typenum[2]+=24;
                break;
            default:
                for(int i=0;i<typenum.length;++i){
                    typenum[i]+=8;
                }
        }

        switch (userModel.getInterest_2().toUpperCase(Locale.ROOT)){
            case "ICCV":
                typenum[0]+=18;
                break;
            case "ECCV":
                typenum[1]+=18;
                break;
            case "CVPR":
                typenum[2]+=18;
                break;
            default:
                for(int i=0;i<typenum.length;++i){
                    typenum[i]+=6;
                }
        }

        switch (userModel.getInterest_2().toUpperCase(Locale.ROOT)){
            case "ICCV":
                typenum[0]+=12;
                break;
            case "ECCV":
                typenum[1]+=12;
                break;
            case "CVPR":
                typenum[2]+=12;
                break;
            default:
                for(int i=0;i<typenum.length;++i){
                    typenum[i]+=4;
                }
        }


        //System.out.println(typenum[0]);
        //System.out.println(typenum[1]);
        //System.out.println(typenum[2]);

        List<ArticleModel> articleModelList=articleService.getArticleByType("ICCV",typenum[0]);
        //System.out.println("1234");
        List<ArticleModel> articleModelList2=articleService.getArticleByType("ECCV",typenum[1]);
        List<ArticleModel> articleModelList3=articleService.getArticleByType("CVPR",typenum[2]);
        articleModelList.addAll(articleModelList2);
        articleModelList.addAll(articleModelList3);
        //若获取的对应用户信息不存在
        if(articleModelList.size()==0){
            throw new BusinessException(EmBusinessError.ARTICLE_NOT_EXIST);
        }

        return CommonReturnType.create(articleModelList);
    }

    //根据source获取文章信息
    @RequestMapping(value = "/recommendBySource")
    @ResponseBody
    public CommonReturnType recommendArticleBySource(@RequestParam(name="source") String source,
                                                     @RequestParam(name="email") String email) throws BusinessException {


        UserModel userModel=userService.getUserByEmail(email);
        //若获取的对应用户信息不存在
        if(userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        /*

         */
        List<ArticleModel> articleModelList=articleService.getArticleByType(source,50);
        if(articleModelList.size()==0){
            throw new BusinessException(EmBusinessError.ARTICLE_NOT_EXIST);
        }

        return CommonReturnType.create(articleModelList);
    }






}
