package com.example.Beans;

import com.example.DataBase.DataBaseConnection;
import com.example.DataBase.DataBaseManager;
import com.example.MBeans.MBeanRegistrar;
import com.example.Services.ResultCheck;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

@Setter
@Getter
@Named("dotBean")
@RequestScoped

public class DotBean {
    private double x;
    private String y;
    private String r;
    private Boolean success;

    @Inject
    private TableBean tableBean;

    public void process() throws SQLException {
        check();

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        DataBaseManager dataBaseManager = new DataBaseManager(dataBaseConnection);

        double yd = Double.parseDouble(y);
        double rd = Double.parseDouble(r);
        dataBaseManager.addData(x, yd, rd, success);
        tableBean.addPoint(new DotPoint(x, y, r, success));
        if (MBeanRegistrar.pointStats != null) {
            MBeanRegistrar.pointStats.recordPoint(success);
        }
        if (MBeanRegistrar.area != null) {
            MBeanRegistrar.area.setR(rd);
        }
    }

    public void check() {
        boolean flag = ResultCheck.resultCheck(x, y, r);
        setSuccess(flag);
    }

    public void validateY(FacesContext context, UIComponent component, Object value) {

        String yStr = value.toString();

        if (yStr.matches(".*[A-Za-zА-Яа-яёЁ].*")) {
            ((UIInput) component).setValid(false);
            FacesMessage message = new FacesMessage("Некорректный формат числа Y");
            context.addMessage(component.getClientId(context), message);
        }
    }
}
