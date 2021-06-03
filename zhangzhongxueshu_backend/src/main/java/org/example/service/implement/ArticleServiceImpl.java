package org.example.service.implement;

import org.example.dao.ArticleDOMapper;
import org.example.dao.UserDOMapper;
import org.example.dataobj.ArticleDO;
import org.example.dataobj.RecomInfoDO;
import org.example.dataobj.UserDO;
import org.example.dataobj.UserPasswordDO;
import org.example.service.ArticleService;
import org.example.service.UserService;
import org.example.service.model.ArticleModel;
import org.example.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDOMapper articleDOMapper;

    @Override
    public List<ArticleModel> getArticleByTitle(String title) {
        //调用userdomapper获取到对应的用户dataobject

        List<ArticleDO> articleDOList=articleDOMapper.selectByTitle(title);
        List<ArticleModel> articleModelList=new ArrayList<>();

        for(int i=0;i<articleDOList.size();++i){
            articleModelList.add(convertFromDataObject(articleDOList.get(i)));
        }
        return articleModelList;

    }

    @Override
    public List<ArticleModel> getArticleByType(String source, Integer num) {
        //调用userdomapper获取到对应的用户dataobject
        //System.out.println(source);
        List<ArticleDO> articleDOList=articleDOMapper.getArticleByType(source);
        //System.out.println("123141");
        //System.out.println(articleDOList.size());
        List<ArticleModel> articleModelList=new ArrayList<>();


        int[] integerList=randomCommon(0, articleDOList.size()-1,num);

        for(int i=0;i<integerList.length;++i){
            articleModelList.add(convertFromDataObject(articleDOList.get(integerList[i])));
        }

        return articleModelList;
    }

    private ArticleModel convertFromDataObject(ArticleDO articleDO){
        if(articleDO==null) {
            return null;
        }

        ArticleModel articleModel=new ArticleModel();
        BeanUtils.copyProperties(articleDO,articleModel);


        return articleModel;
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }
}
