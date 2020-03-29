/*
 * 作者：刘时明
 * 时间：2020/3/29-21:28
 * 作用：
 */
package com.novel.reptile.facade;

import com.novel.reptile.facade.jinyong.JinyongReptileFacade;
import com.novel.reptile.facade.zongheng.ZonghengReptileFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReptileBuild
{
    @Autowired
    private ZonghengReptileFacade zonghengReptileFacade;
    @Autowired
    private JinyongReptileFacade jinyongReptileFacade;

    private ReptileType reptileType;

    private ReptileConfig reptileConfig;

    public ReptileBuild()
    {
        this.reptileConfig = new ReptileConfig();
    }

    public ReptileBuild type(ReptileType reptileType)
    {
        this.reptileType = reptileType;
        return this;
    }

    public ReptileBuild name(String name)
    {
        this.reptileConfig.setName(name);
        return this;
    }

    public ReptileBuild start(int start)
    {
        this.reptileConfig.setStart(start);
        return this;
    }

    public ReptileBuild end(int end)
    {
        this.reptileConfig.setEnd(end);
        return this;
    }

    public ReptileStart build()
    {
        if (reptileType == null) return null;
        switch (reptileType)
        {
            case JINYONG:
                jinyongReptileFacade.init(this.reptileConfig);
                return jinyongReptileFacade;
            case ZONGHENG:
                zonghengReptileFacade.init(this.reptileConfig);
                return zonghengReptileFacade;
            default:
                return null;
        }
    }
}
