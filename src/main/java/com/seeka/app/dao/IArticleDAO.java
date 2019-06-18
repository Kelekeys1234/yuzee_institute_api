package com.seeka.app.dao;import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.PageLookupDto;
import com.seeka.app.dto.SearchDto;

public interface IArticleDAO {

    public List<SeekaArticles> getAll();

    public List<SeekaArticles> getArticlesByLookup(PageLookupDto pageLookupDto);

    public SeekaArticles findById(BigInteger uId);

    public void deleteArticle(SeekaArticles article);

    public List<SeekaArticles> fetchAllArticleByPage(BigInteger page, BigInteger size, String query, boolean status);

    public int findTotalCount();

    public SeekaArticles save(SeekaArticles article);

    public void updateArticle(BigInteger subCAtegory, BigInteger id);

    public List<SeekaArticles> searchArticle(SearchDto article);
}
