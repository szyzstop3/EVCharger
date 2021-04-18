package com.example.evcharger.DAO;

import com.example.evcharger.vo.Charger;
import com.example.evcharger.vo.Comment;

public interface ToolDAO {
    //判断二维码和充电桩信息是否有效
    public boolean validCharger(Charger charger);
    //获取充电桩信息返回JSON格式
    public String getCharger(Charger charger);
    //上传用户评论信息
    public void addComment(Comment comment);
    //注册充电桩
    public void registcharger(Charger charger);

}
