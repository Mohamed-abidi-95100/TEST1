package com.example.stage.service;

import com.example.stage.dao.ChercheurEmploiDAO;
import com.example.stage.model.ChercheurEmploi;

public class ChercheurEmploiService {
    private ChercheurEmploiDAO chercheurEmploiDAO = new ChercheurEmploiDAO();

    public boolean creerChercheurEmploi(ChercheurEmploi chercheurEmploi) {
        return chercheurEmploiDAO.creerChercheurEmploi(chercheurEmploi);
    }
}