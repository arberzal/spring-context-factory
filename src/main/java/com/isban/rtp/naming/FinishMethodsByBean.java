/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isban.rtp.naming;

import java.util.List;

/**
 *
 * @author arberzal
 */
public class FinishMethodsByBean {
    FinishMethodsByClass finishMethodsByClass;
    private List<String> beanNames;

    public FinishMethodsByClass getFinishMethodsbyClass() {
        return finishMethodsByClass;
    }

    public void setFinishMethodsByClass(FinishMethodsByClass finishMethods) {
        this.finishMethodsByClass = finishMethods;
    }

    public List<String> getBeanNames() {
        return beanNames;
    }
    
    public void setBeanNames(List<String> beanNames_) {
        beanNames = beanNames_;        
    }
}
