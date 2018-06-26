package pro.xway.controller;

import pro.xway.jms.EnqueueBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("mainPage")
@ApplicationScoped
public class MainPage implements Serializable {
    @Inject
    private EnqueueBean enqueueBean;
    private String message;
    private boolean notShowBtn = true;

    public void sendMessage(){
        enqueueBean.sendMessage(message);
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isNotShowBtn() {
        return notShowBtn;
    }

    public void setNotShowBtn(boolean notShowBtn) {
        this.notShowBtn = notShowBtn;
    }

}
