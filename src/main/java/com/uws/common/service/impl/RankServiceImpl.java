package com.uws.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.IRankDao;
import com.uws.common.service.IRankService;
import com.uws.core.base.BaseModel;
import com.uws.core.base.BaseServiceImpl;

/**
 *	公用排序算法实现
 */
@Service("rankService")
public class RankServiceImpl extends BaseServiceImpl implements IRankService{

	@Autowired
	private IRankDao rankDao;

	@Override
	public void moveUpObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception {
		//下移紧前对象
		this.rankDao.moveDownLastObject(bm,rankColumn,rankValue,fkColumn,fkValue);
		//上移当前对象
		this.rankDao.moveUpSelf(bm, rankColumn);
	}

	@Override
	public void moveDownObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception {
		//上移紧后对象
		this.rankDao.moveUpNextObject(bm,rankColumn,rankValue,fkColumn,fkValue);
		//下移当前对象
		this.rankDao.moveDownSelf(bm, rankColumn);
	}

	@Override
	public void deleteCurObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception {
		//上移所有紧后对象
		this.rankDao.moveUpAll(bm,rankColumn,rankValue,fkColumn,fkValue);
		//物理删除当前对象
		this.rankDao.delete(bm);
	}

	@Override
	public boolean isMaxSeq(BaseModel  bm, String rankColumn,int rankValue,String fkColumn, String foreignKeyValue) {
		return this.rankDao.isMaxSeq(bm, rankColumn,rankValue, fkColumn, foreignKeyValue);
	}

	@Override
	public boolean isMinSeq(BaseModel  bm, String rankColumn,int rankValue,String fkColumn, String foreignKeyValue) {
		return this.rankDao.isMinSeq(bm, rankColumn,rankValue,fkColumn, foreignKeyValue);
	}

}
