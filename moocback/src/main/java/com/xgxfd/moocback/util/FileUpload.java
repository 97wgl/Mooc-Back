package com.xgxfd.moocback.util;

import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 王桂林
 * @Title: FileUpload
 * @ProjectName moocback
 * @Description: TODO
 * @date 2019/5/3018:43
 */
@Slf4j
public class FileUpload {

    //private String filePath = "F:\\个人\\彭世维毕业设计\\MOOC\\Project\\Mooc-Back\\moocback\\src\\main\\resources\\static\\";
    private String filePath = "D:\\慕课远程教育项目\\Mooc-Back\\moocback\\src\\main\\resources\\static\\";
    /**
     *
     * @param file  文件
     * @param type  文件类型
     * @return
     */
    public MessageVO<String> upload(MultipartFile file, String type) {
        MessageVO<String> messageVO = new MessageVO<>();
        if (file.isEmpty()) {
            log.error("未选择文件!");
            messageVO.setCode(-1);
            messageVO.setMsg("失败！");
            return messageVO;
        }
        String fileName = file.getOriginalFilename();
        filePath = filePath + type + "\\";
        File dir = new File(filePath);
        log.info(filePath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        String[] fileNameSplit = fileName.split("\\.");
        fileName = UUID.randomUUID() + "." + fileNameSplit[fileNameSplit.length - 1];
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            log.info(type + "类型文件保存成功！");
            messageVO.setCode(0);
            messageVO.setMsg("保存成功！");
            messageVO.setData(fileName);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return messageVO;
    }
}
