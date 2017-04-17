package com.uws.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.IRankDao;
import com.uws.core.base.BaseModel;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.util.DataUtil;
import com.uws.log.Logger;
import com.uws.log.LoggerFactory;

@Repository("rankDao")
public class RankDaoImpl extends BaseDaoImpl implements IRankDao{
	
	// 日志
	private Logger logger = new LoggerFactory(RankDaoImpl.class);

	@Override
	public void moveUpSelf(BaseModel bm, String rankColumn) {
		String  className = bm.getClass().getName();
		String  hql=" update "+className+" ref set ref."+rankColumn+" =ref."+rankColumn+"-1 where ref.id=?";
		if(!DataUtil.isNotNull(bm)){
			logger.error("排序对象不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(bm.getId())){
			logger.error("排序对象【"+className+"】不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(rankColumn)){
			logger.error("排序字段不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else{
			this.executeHql(hql.toString(), new Object[]{bm.getId()});
		}
	}

	@Override
	public void moveDownSelf(BaseModel bm, String rankColumn) {
		String  className = bm.getClass().getName();
		String  hql=" update "+className+" ref set ref."+rankColumn+" =ref."+rankColumn+"+1 where ref.id=?";
		if(!DataUtil.isNotNull(bm)){
			logger.error("排序对象不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(bm.getId())){
			logger.error("排序对象【"+className+"】不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(rankColumn)){
			logger.error("排序字段不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else{
			this.executeHql(hql.toString(), new Object[]{bm.getId()});
		}
	}

	@Override
	public void moveUpNextObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception {
		List<Object> values = new ArrayList<Object>();
		String  className = bm.getClass().getName();
		StringBuffer  hql = new StringBuffer();
		hql.append(" update "+className+" ref set ref."+rankColumn+" =?  where ref."+rankColumn+"=?");
		values.add(rankValue);//上移
		values.add(rankValue+1);//紧后对象
		if(DataUtil.isNotNull(fkColumn)||DataUtil.isNotNull(fkValue)){
			hql.append("  and ref."+fkColumn+".id =?");
			values.add(fkValue);
		}
		
		if(!DataUtil.isNotNull(bm)){
			logger.error("排序对象不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(bm.getId())){
			logger.error("排序对象【"+className+"】不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(rankColumn)){
			logger.error("排序字段不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(rankValue)){
			logger.error("排序条件不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else{
			this.moveOtherObjHql(hql.toString(),values);
		}
	}

	@Override
	public void moveDownLastObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception {
		List<Object> values = new ArrayList<Object>();
		String  className = bm.getClass().getName();
		StringBuffer  hql = new StringBuffer();
		hql.append(" update "+className+" ref set ref."+rankColumn+" =?  where ref."+rankColumn+"=? ");
		values.add(rankValue);//下移
		values.add(rankValue-1);//紧前对象
		if(DataUtil.isNotNull(fkColumn)||DataUtil.isNotNull(fkValue)){
			hql.append(" and ref."+fkColumn+".id =?");
			values.add(fkValue);
		}
		
		if(!DataUtil.isNotNull(bm)){
			logger.error("排序对象不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(bm.getId())){
			logger.error("排序对象【"+className+"】不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(rankColumn)){
			logger.error("排序字段不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(rankValue)){
			logger.error("排序条件不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else{
			this.moveOtherObjHql(hql.toString(),values);
		}
	}

	@Override
	public void moveUpAll(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception {
		List<Object> values = new ArrayList<Object>();
		String  className = bm.getClass().getName();
		StringBuffer  hql = new StringBuffer();
		hql.append(" update "+className+" ref set ref."+rankColumn+" = ref."+rankColumn+"-1  where ref."+rankColumn+">? ");
		values.add(rankValue);//所有紧后对象上移
		if(DataUtil.isNotNull(fkColumn)||DataUtil.isNotNull(fkValue)){
			hql.append(" and ref."+fkColumn+".id =?");
			values.add(fkValue);
		}
		
		if(!DataUtil.isNotNull(bm)){
			logger.error("排序对象不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(bm.getId())){
			logger.error("排序对象【"+className+"】不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(rankColumn)){
			logger.error("排序字段不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else if(!DataUtil.isNotNull(rankValue)){
			logger.error("排序条件不存在 !\r\n");
			logger.error("排序语句："+hql);
		}else{
			this.moveOtherObjHql(hql.toString(),values);
		}
	}

	/**
	 * 移动需要和当前对象互换位置的对象
	 * @param hql
	 * @param values
	 */
	private void moveOtherObjHql(String hql,List<Object> values) {
		if(DataUtil.isNotNull(hql) && DataUtil.isNotNull(values)){
			this.executeHql(hql.toString(), values.toArray());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isMaxSeq(BaseModel bm, String rankColumn,int rankValue,String fkColumn,String fkValue) {
		String  className = bm.getClass().getName();
		String hql=" from "+className+" where "+fkColumn+".id =? and "+rankColumn+" > ?";
		List<BaseModel>  results = this.query(hql, new Object[]{fkValue,rankValue});
		if(DataUtil.isNotNull(results) && results.size()>0) return false;
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isMinSeq(BaseModel bm, String rankColumn,int rankValue,String fkColumn,String fkValue) {
		String  className = bm.getClass().getName();
		String hql=" from "+className+" where "+fkColumn+".id =? and "+rankColumn+" < ?";
		List<BaseModel>  results = this.query(hql, new Object[]{fkValue,rankValue});
		if(DataUtil.isNotNull(results)  && results.size()>0) return false;
		return true;
	}

}
