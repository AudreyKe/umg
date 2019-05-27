package com.weshare.umg.controller;


import com.weshare.umg.exception.UmgException;
import com.weshare.umg.response.base.Result;
import com.weshare.umg.response.base.ResultUtil;
import com.weshare.umg.service.mail.MailService;
import com.weshare.umg.service.task.UndeliveredMailTask;
import com.weshare.umg.util.CodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/mail")
public class MailController {

    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    ApplicationContext context;

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    @ResponseBody
    public Result task() {
        UndeliveredMailTask undeliveredMailTask = new UndeliveredMailTask();
        undeliveredMailTask.scanUndeliveredMail(context);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public Result send(@RequestBody String requestBody) {
        try {
            mailService.acceptMail(requestBody);
        }catch (UmgException ge){
            return ResultUtil.error(ge.getCode(),ge.getMessage());
        }catch (Exception e){
            return ResultUtil.error(CodeEnum.SYSTEM_ERROR.getCode(),CodeEnum.SYSTEM_ERROR.getMessage());
        }
        return ResultUtil.success();
    }


}
