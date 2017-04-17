package com.uws.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.IQuestionNaireDao;
import com.uws.common.util.AmsDateUtil;
import com.uws.common.util.Constants;
import com.uws.common.util.QuestionNaireConstants;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.DataUtil;
import com.uws.core.util.DateUtil;
import com.uws.domain.question.QuestionAnswerBaseModel;
import com.uws.domain.question.QuestionAnswerDetailModel;
import com.uws.domain.question.QuestionInfoItemModel;
import com.uws.domain.question.QuestionInfoModel;
import com.uws.domain.question.QuestionItemOptionModel;

@Repository("questionNaireDao")
public class QuestionNaireDaoImpl extends BaseDaoImpl implements IQuestionNaireDao {

	/**
	 * 判断答题人待回答的问卷是否已提交
	 * @param respondent							答题人
	 * @param questionNaireId				问卷Id
	 * @return													[true：已提交，false：未提交]
	 */
	@Override
	public boolean isSubmitQuestionNaire(String respondent,String questionNaireId) {
		boolean returnValue=false;
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer(" from QuestionAnswerBaseModel qabm where 1=1");
		if(DataUtil.isNotNull(respondent) && DataUtil.isNotNull(questionNaireId)){
			hql.append(" and qabm.respondent.id=?");
			hql.append(" and qabm.questionInfo.id=?");
			hql.append(" and qabm.answerStatus.id=?");
			values.add(respondent);
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ANSWER_STATUS_COMMITED.getId());
			QuestionAnswerBaseModel qabm = (QuestionAnswerBaseModel)this.queryUnique(hql.toString(), values.toArray());
			if(DataUtil.isNotNull(qabm))
				returnValue = true;
		}
		return returnValue;
	}

	/**
	 * 获取当前登录人保存的答卷
	 * @param currentUserId					答题人
	 * @param questionNaireId				问卷Id
	 * @return													当前登陆人保存的问卷
	 */
	@Override
	public QuestionAnswerBaseModel getQuesNaireBaseModel(String currentUserId,String questionNaireId) {
		QuestionAnswerBaseModel qabm = null;
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer(" from QuestionAnswerBaseModel qabm where 1=1");
		if(DataUtil.isNotNull(currentUserId) && DataUtil.isNotNull(questionNaireId)){
			hql.append(" and qabm.respondent.id=?");
			hql.append(" and qabm.questionInfo.id=?");
			hql.append(" and qabm.answerStatus.id=?");
			values.add(currentUserId);
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ANSWER_STATUS_UNCOMMITED.getId());
			qabm = (QuestionAnswerBaseModel)this.queryUnique(hql.toString(), values.toArray());
		}
		
		return qabm;
	}

	/**
	 * 获取答题人的答题记录
	 * @param respondent							答题人
	 * @param questionNaireId				问卷Id
	 * @return													问卷答题记录
	 */
	@Override
	public QuestionAnswerBaseModel getQuestionNaireBaseInfo(String respondent,String questionNaireId) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer(" from QuestionAnswerBaseModel qabm where 1=1");
		if(DataUtil.isNotNull(respondent) && DataUtil.isNotNull(questionNaireId)){
			hql.append(" and qabm.respondent.id=?");
			hql.append(" and qabm.questionInfo.id=?");
			values.add(respondent);
			values.add(questionNaireId);
			QuestionAnswerBaseModel qabm = (QuestionAnswerBaseModel)this.queryUnique(hql.toString(), values.toArray());
			return qabm;
		}
		return null;
	}
	
	/**
	 * 获取答题人回答的某个答卷
	 * @param respondent				答题人
	 * @param questionNaireId	答卷ID
	 * @return										答卷对象
	 */
	public QuestionAnswerBaseModel getQuestionNaire(String respondent,String questionNaireId) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer(" from QuestionAnswerBaseModel qabm where 1=1");
		if(DataUtil.isNotNull(respondent) && DataUtil.isNotNull(questionNaireId)){
			hql.append(" and qabm.respondent.id=?");
			hql.append(" and qabm.questionInfo.id=?");
			values.add(respondent);
			values.add(questionNaireId);
			QuestionAnswerBaseModel qabm = (QuestionAnswerBaseModel)this.queryUnique(hql.toString(), values.toArray());
			if(DataUtil.isNotNull(qabm))
				return qabm;
		}
		return new QuestionAnswerBaseModel();
	}
	
	/**
	 * 获取问卷信息
	 * @param questionNaireId				问卷主键ID
	 * @return													问卷对象信息
	 */
	@Override
	public QuestionInfoModel getQuestionNaireInfo(String questionNaireId) {
		StringBuffer hql=new StringBuffer(" from QuestionInfoModel qim where 1=1");
		List<String> values = new ArrayList<String>();
		if(DataUtil.isNotNull(questionNaireId)){
			hql.append(" and qim.id=?");
			values.add(questionNaireId);
			return (QuestionInfoModel) this.queryUnique(hql.toString(), values.toArray());
		}
		return new QuestionInfoModel();
	}
	
	/**
	 * 获取问卷信息
	 * @param questionNaireId				问卷主键ID
	 * @return													问卷对象信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionInfoModel> getQuestionNaireInfoList() {
			StringBuffer hql=new StringBuffer(" from QuestionInfoModel qim where 1=1");
			List<String> values = new ArrayList<String>();
			hql.append("  and qim.statusDic.id=?");
			hql.append("  and qim.delStatusDic.id=?");
			values.add(QuestionNaireConstants.PAPER_STATUS_ENABLE.getId());
			values.add(Constants.STATUS_NORMAL.getId());
			return this.query(hql.toString(), values.toArray());
	}

	/**
	 * 获取新生待答的【迎新、公共】问卷列表
	 * @param respondent							答题人
	 * @return													问卷列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionInfoModel> getNewStudentQuestionNaireInfo(String respondent) {
		List<QuestionInfoModel> questionNaireList = new ArrayList<QuestionInfoModel>();
		List<Object> vlaues = new ArrayList<Object>();
		if(DataUtil.isNotNull(respondent)){
			StringBuffer hql=new StringBuffer("select qnrm.questionNairePo from QuestionNaireRespondentModel qnrm where 1=1");
			hql.append(" and qnrm.respondent.id=?");
			hql.append(" and qnrm.questionNairePo.statusDic.id=?");
			hql.append(" and qnrm.questionNairePo.delStatusDic.id=?");
			hql.append(" and qnrm.userType ='"+QuestionNaireConstants.RESPONDENT_TYPE_ENUMS.NEW_STUDENT+"'");
			hql.append(" and qnrm.questionNairePo.typeDic.code in('");
			hql.append(QuestionNaireConstants.QUESTIONNAIRE_TYPE_ENUMS.QUES_COMMON).append("','");
			hql.append(QuestionNaireConstants.QUESTIONNAIRE_TYPE_ENUMS.QUES_ORIENTATION).append("')");
			vlaues.add(respondent);
			vlaues.add(QuestionNaireConstants.PAPER_STATUS_ENABLE.getId());
			vlaues.add(Constants.STATUS_NORMAL.getId());
			questionNaireList =  this.query(hql.toString(),vlaues.toArray());
		}
		
		List<QuestionInfoModel>  openPaperList = this.getOpenNaireList() ;
		for(QuestionInfoModel qim:openPaperList){
			questionNaireList.add(qim);
		}
		
		return questionNaireList;
	}
	
	/**
	 * 获取公开的问卷
	 * @param respondent		答题人
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<QuestionInfoModel> getOpenNaireList() {
		List<Object> vlaues = new ArrayList<Object>();
		StringBuffer hql=new StringBuffer("from QuestionInfoModel qim where 1=1");
		hql.append(" and qim.isOpen=?");
		hql.append(" and qim.statusDic.id=?");
		hql.append(" and qim.delStatusDic.id=?");
		vlaues.add("Y");
		vlaues.add(QuestionNaireConstants.PAPER_STATUS_ENABLE.getId());
		vlaues.add(Constants.STATUS_NORMAL.getId());
		return  this.query(hql.toString(),vlaues.toArray());
	}
	
	/**
	 * 获取当前问卷的单选题列表
	 * @param questionNaireId				问卷ID
	 * @return													问卷单选题列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionInfoItemModel> getQuestionNaireSingleItemList(String questionNaireId) {
		StringBuffer hql = new StringBuffer();
		List<String> values = new ArrayList<String>();
		if(DataUtil.isNotNull(questionNaireId)){
			hql.append(" from QuestionInfoItemModel qim where 1=1");
			hql.append(" and qim.questionInfo.id=?");
			hql.append(" and qim.itemType.id=?");
			hql.append(" order by qim.quesSeqNum asc");
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ITEM_TYPE_SINGLE.getId());
			return this.query(hql.toString(), values.toArray());
		}
		return new ArrayList<QuestionInfoItemModel>();
	}
	
	/**
	 * 获取当前问卷的多选题列表
	 * @param questionNaireId				问卷ID
	 * @return													问卷多选题列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionInfoItemModel> getQuestionNaireMulItemList(String questionNaireId) {
		StringBuffer hql = new StringBuffer();
		List<String> values = new ArrayList<String>();
		if(DataUtil.isNotNull(questionNaireId)){
			hql.append(" from QuestionInfoItemModel qim where 1=1");
			hql.append(" and qim.questionInfo.id=?");
			hql.append(" and qim.itemType.id=?");
			hql.append(" order by qim.quesSeqNum asc");
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ITEM_TYPE_MULTIPLE.getId());
			return this.query(hql.toString(), values.toArray());
		}
		return new ArrayList<QuestionInfoItemModel>();
	}
	
	/**
	 * 获取当前问卷的问答题列表
	 * @param questionNaireId				问卷ID
	 * @return													问卷问答题列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionInfoItemModel> getQuestionNaireSubItemList(String questionNaireId) {
		StringBuffer hql = new StringBuffer();
		List<String> values = new ArrayList<String>();
		if(DataUtil.isNotNull(questionNaireId)){
			hql.append(" from QuestionInfoItemModel qim where 1=1");
			hql.append(" and qim.questionInfo.id=?");
			hql.append(" and qim.itemType.id=?");
			hql.append(" order by qim.quesSeqNum asc");
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ITEM_TYPE_MULESSAY_QUESTION.getId());
			return this.query(hql.toString(), values.toArray());
		}
		return new ArrayList<QuestionInfoItemModel>();
	}
	
	/**
	 * 获取当前问卷的单选题选项列表
	 * @param questionNaireId				问卷ID
	 * @return													问卷单选题选项列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionItemOptionModel> getSingleQuestionOption(String questionNaireId) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer();
		if(DataUtil.isNotNull(questionNaireId)){
			hql.append(" from QuestionItemOptionModel qiom where 1=1");
			hql.append(" and qiom.questionNaire.id=?");
			hql.append(" and qiom.itemType.id=?");
			hql.append(" order by qiom.seqnum asc");
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ITEM_TYPE_SINGLE.getId());
			return this.query(hql.toString(), values.toArray());
		}
		return new ArrayList<QuestionItemOptionModel>();
	}

	/**
	 * 获取当前问卷单选题答案列表				
	 * @param respondent							答题人
	 * @param questionNaireId				问卷ID
	 * @return													问卷单选题答案列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionAnswerDetailModel> getSingleQuestionAnswer(String respondent, String questionNaireId) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer(" from QuestionAnswerDetailModel qadm where 1=1");
		if(DataUtil.isNotNull(respondent) && DataUtil.isNotNull(questionNaireId)){
			hql.append(" and qadm.respondent.id=?");
			hql.append(" and qadm.questionInfo.id=?");
			hql.append(" and qadm.itemType.id=?");
			hql.append(" order by qadm.itemSeq,qadm.optionSeq asc");
			values.add(respondent);
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ITEM_TYPE_SINGLE.getId());
			return this.query(hql.toString(), values.toArray());
		}
		return new ArrayList<QuestionAnswerDetailModel>();
	}
	
	/**
	 * 获取当前问卷多选题选项列表				
	 * @param questionNaireId				问卷ID
	 * @return													问卷多选题选项列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionItemOptionModel> getMulQuestionOption(String questionNaireId) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer();
		if(DataUtil.isNotNull(questionNaireId)){
			hql.append(" from QuestionItemOptionModel qiom where 1=1");
			hql.append(" and qiom.questionNaire.id=?");
			hql.append(" and qiom.itemType.id=?");
			hql.append(" order by qiom.seqnum asc");
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ITEM_TYPE_MULTIPLE.getId());
			return this.query(hql.toString(), values.toArray());
		}
		return new ArrayList<QuestionItemOptionModel>();
	}

	/**
	 * 获取当前问卷多选题答案列表
	 * @param respondent							答题人
	 * @param questionNaireId				问卷ID
	 * @return													问卷多选题答案列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionAnswerDetailModel> getMulQuestionAnswer(String respondent, String questionNaireId) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer(" from QuestionAnswerDetailModel qadm where 1=1");
		if(DataUtil.isNotNull(respondent) && DataUtil.isNotNull(questionNaireId)){
			hql.append(" and qadm.respondent.id=?");
			hql.append(" and qadm.questionInfo.id=?");
			hql.append(" and qadm.itemType.id=?");
			hql.append(" order by qadm.itemSeq,qadm.optionSeq asc");
			values.add(respondent);
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ITEM_TYPE_MULTIPLE.getId());
			return this.query(hql.toString(), values.toArray());
		}
		return new ArrayList<QuestionAnswerDetailModel>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionItemOptionModel> getSubQuestionOption(String questionNaireId) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer();
		if(DataUtil.isNotNull(questionNaireId)){
			hql.append(" from QuestionItemOptionModel qiom where 1=1");
			hql.append(" and qiom.questionNaire.id=?");
			hql.append(" and qiom.itemType.id=?");
			hql.append(" order by qiom.seqnum asc");
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ITEM_TYPE_MULESSAY_QUESTION.getId());
			return this.query(hql.toString(), values.toArray());
		}
		return new ArrayList<QuestionItemOptionModel>();
	}
	
	/**
	 * 获取当前问卷问答题答案列表
	 * @param respondent							答题人
	 * @param questionNaireId				问卷ID
	 * @return													问卷问答题答案列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionAnswerDetailModel> getSubQuestionAnswer(String respondent, String questionNaireId) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer(" from QuestionAnswerDetailModel qadm where 1=1");
		if(DataUtil.isNotNull(respondent) && DataUtil.isNotNull(questionNaireId)){
			hql.append(" and qadm.respondent.id=?");
			hql.append(" and qadm.questionInfo.id=?");
			hql.append(" and qadm.itemType.id=?");
			hql.append(" order by qadm.itemSeq,qadm.optionSeq asc");
			values.add(respondent);
			values.add(questionNaireId);
			values.add(QuestionNaireConstants.ITEM_TYPE_MULESSAY_QUESTION.getId());
			return this.query(hql.toString(), values.toArray());
		}
		return new ArrayList<QuestionAnswerDetailModel>();
	}
	
	/**
	 * 修改并保存答卷基本信息
	 * @param qadm					答卷基本信息对象
	 */
	public void saveExistPaper(QuestionAnswerBaseModel qabm) {
		qabm.setAnswerStatus(QuestionNaireConstants.ANSWER_STATUS_UNCOMMITED);
		qabm.setUpdateTime(DateUtil.getDate());
		this.update(qabm);
	}

	/**
	 * 保存问卷答案
	 * @param singleQadms						单选题信息
	 * @param mulQadms							多选题信息
	 * @param answerQadms					问答题信息
	 */
	@Override
	public void saveQuestionNaire(
			List<QuestionAnswerDetailModel> singleQadms, 
			List<QuestionAnswerDetailModel> mulQadms,
			List<QuestionAnswerDetailModel> answerQadms) {
		this.saveItemAnswer(singleQadms);
		this.saveItemAnswer(mulQadms);
		this.saveItemAnswer(answerQadms);
	}

	/**
	 * 提交问卷答案
	 * @param singleQadms						单选题信息
	 * @param mulQadms							多选题信息
	 * @param answerQadms					问答题信息
	 */
	@Override
	public void submitQuestionNaire(
			List<QuestionAnswerDetailModel> singleQadms, 
			List<QuestionAnswerDetailModel> mulQadms,
			List<QuestionAnswerDetailModel> answerQadms) {
		this.saveItemAnswer(singleQadms);
		this.saveItemAnswer(mulQadms);
		this.saveItemAnswer(answerQadms);
	}
	
	/**
	 * 保存答卷信息
	 * @param qabm			答卷基本信息对象
	 */
	@Override
	public void savePaper(QuestionAnswerBaseModel qabm) {
		qabm.setAnswerStatus(QuestionNaireConstants.ANSWER_STATUS_UNCOMMITED);
		if(DataUtil.isNotNull(qabm) && DataUtil.isNotNull(qabm.getId())){
			this.update(qabm);
		}else{
			this.save(qabm);
		}
	}
	
	/**
	 * 提交当前答卷的新增操作
	 * @param qabm
	 */
	@Override
	public void submitPaper(QuestionAnswerBaseModel qabm){
		qabm.setAnswerStatus(QuestionNaireConstants.ANSWER_STATUS_COMMITED);
		if(DataUtil.isNotNull(qabm) && DataUtil.isNotNull(qabm.getId())){
			this.update(qabm);
		}else{
			this.save(qabm);
		}
	}
	
	/**
	 * 保存问题答案
	 * @param singleQadms			单选题答案数组
	 */
	public void saveItemAnswer(List<QuestionAnswerDetailModel> answers) {
		for(QuestionAnswerDetailModel qadm:answers){
			if(DataUtil.isNotNull(qadm)){
				String itemType = qadm.getItemType().getId();
				if(QuestionNaireConstants.ITEM_TYPE_MULESSAY_QUESTION.getId().equals(itemType)){
					this.save(qadm);
				}else{
					String isCheckdOption = qadm.getChecked();
					String checkedFlag = QuestionNaireConstants.INPUT_FLAG_ENUMS.CHECKED.toString();
					if(checkedFlag.equalsIgnoreCase(isCheckdOption)){
						this.save(qadm);
					}
				}
			}
		}
	}
	
	@Override
	public void deletePaperDetailInfo(String respondent, String questionNaireId) {
		if(DataUtil.isNotNull(respondent) & DataUtil.isNotNull(questionNaireId)){
			StringBuffer hql = new StringBuffer(" delete from QuestionAnswerDetailModel qadm where 1=1");
			hql.append(" and qadm.respondent.id=?");
			hql.append(" and qadm.questionInfo.id=?");
			this.executeHql(hql.toString(), new Object[]{respondent,questionNaireId});
		}
	}

	@Override
	public void deletePaperBaseInfo(String respondent, String questionNaireId) {
		if(DataUtil.isNotNull(respondent) & DataUtil.isNotNull(questionNaireId)){
			StringBuffer hql = new StringBuffer(" delete from QuestionAnswerBaseModel qabm where 1=1");
			hql.append(" and qabm.respondent.id=?");
			hql.append(" and qabm.questionInfo.id=?");
			this.executeHql(hql.toString(), new Object[]{respondent,questionNaireId});
		}
	}

	
	/**
	 * 描述信息: 按类型条件和答题人查询问卷信息
	 * @param respondent
	 * @param typeDicCode
	 * @param flag
	 * @return
	 * @see com.uws.common.dao.IQuestionNaireDao#getQuestionNaireListByConditions(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public Page queryQuestionPage(String userId,QuestionAnswerBaseModel qabm,int pageNo,int pageSize,boolean flag)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" from QuestionAnswerBaseModel qabm where 1=1");
		if(DataUtil.isNotNull(qabm.getQuestionInfo()) && DataUtil.isNotNull(qabm.getQuestionInfo().getName())){
			hql.append(" and qabm.questionInfo.name like ?");
			values.add("%"+qabm.getQuestionInfo().getName()+"%");
		}
		if(DataUtil.isNotNull(qabm.getQuestionInfo())){
			if(DataUtil.isNotNull(qabm.getQuestionInfo().getTypeDic()) && DataUtil.isNotNull(qabm.getQuestionInfo().getTypeDic().getId())){
				String id = qabm.getQuestionInfo().getTypeDic().getId();
				hql.append(" and qabm.questionInfo.typeDic.id=?");
				values.add(id);
			}
		}
		if(DataUtil.isNotNull(qabm.getAnswerStatus()) && DataUtil.isNotNull(qabm.getAnswerStatus().getId())){
			String id = qabm.getAnswerStatus().getId();
			hql.append(" and qabm.answerStatus.id=?");
			values.add(id);
		}
		
		if(DataUtil.isNotNull(qabm.getBeginDate()) && DataUtil.isNotNull(qabm.getStopDate())){
			hql.append(" and qabm.createTime>= ?");
			values.add(AmsDateUtil.toTime(qabm.getBeginDate()));
			hql.append(" and qabm.createTime<= ?");
			values.add(AmsDateUtil.toTime(qabm.getStopDate()));
		} else if(DataUtil.isNotNull(qabm.getBeginDate()) && DataUtil.isNull(qabm.getStopDate())) {
			hql.append(" and qabm.createTime>= ?");
			values.add(AmsDateUtil.toTime(qabm.getBeginDate()));
		} else if(DataUtil.isNull(qabm.getBeginDate()) && DataUtil.isNotNull(qabm.getStopDate())) {
			hql.append(" and qabm.createTime<= ?");
			values.add(AmsDateUtil.toTime(qabm.getStopDate()));
		}
		
		hql.append(" and qabm.respondent.id=?");
		values.add(userId);
		//排序条件
		hql.append(" order by qabm.updateTime desc ");
	    if(values.size()>0)
	    	return this.pagedQuery(hql.toString(), pageNo, pageSize, values.toArray());
    	return  this.pagedQuery(hql.toString(), pageNo, pageSize);
    }

	/**
	 * 描述信息: 选择问卷查询分页
	 * @param pageNum
	 * @param pageSize
	 * @param currentUserId
	 * @return
	 * 2015-11-5 下午3:20:11
	 */
	@Override
	public Page queryUserQuesInfo(int pageNum,int pageSize,String currentUserId) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" select  qnrm.questionNairePo from QuestionNaireRespondentModel qnrm where 1=1");
		hql.append(" and qnrm.questionNairePo.statusDic.id=?");
		values.add(QuestionNaireConstants.PAPER_STATUS_ENABLE.getId());
		if(DataUtil.isNotNull(currentUserId)){
			hql.append(" and qnrm.respondent.id=?");
			values.add(currentUserId);
		}

		/**
		 * 问卷类型是就业的
		 */
		hql.append(" and qnrm.questionNairePo.typeDic.code=?");
		values.add(QuestionNaireConstants.QUESTIONNAIRE_TYPE_ENUMS.QUES_GRADUATION.toString());
		
		//删除状态
		hql.append(" and qnrm.questionNairePo.delStatusDic.id = '"+Constants.STATUS_NORMAL.getId()+"'");
		//排序条件
		hql.append(" order by qnrm.questionNairePo.createTime desc ");
		
        Page page = null;
	    if(values.size()>0){
	    	page = this.pagedQuery(hql.toString(), pageNum, pageSize, values.toArray());
	    }else{
	    	page = this.pagedQuery(hql.toString(), pageNum, pageSize);
	    }
	    
	    page = this.formatePaperList(page,QuestionNaireConstants.QUESTIONNAIRE_TYPE_ENUMS.QUES_GRADUATION.toString());
	    
		return page;
	}
	/**
	 * 封装【授权问卷+公开问卷】
	 * @param page	分页对象
	 * @return
	 */
	private Page formatePaperList(Page page,String typeCode) {
		List<QuestionInfoModel> paperlist = (ArrayList<QuestionInfoModel>)page.getResult();
		List<QuestionInfoModel>  openPaperList = this.getOpenNaireList(typeCode) ;
		for(QuestionInfoModel qim:openPaperList){
			paperlist.add(qim);
		}
		page.setResult(paperlist);
		return page;
	}
	/**
	 * 获取公开的问卷
	 * @param respondent		答题人
	 * @return
	 */
	public List<QuestionInfoModel> getOpenNaireList(String typeCode) {
		List<Object> vlaues = new ArrayList<Object>();
		StringBuffer hql=new StringBuffer("from QuestionInfoModel qim where 1=1");
		hql.append(" and qim.isOpen=?");
		hql.append(" and qim.statusDic.id=?");
		hql.append(" and qim.delStatusDic.id=?");
		hql.append(" and qim.typeDic.code=?");
		vlaues.add("Y");
		vlaues.add(QuestionNaireConstants.PAPER_STATUS_ENABLE.getId());
		vlaues.add(Constants.STATUS_NORMAL.getId());
		//区分是毕业的还是迎新的
		String code1 = QuestionNaireConstants.QUESTIONNAIRE_TYPE_ENUMS.QUES_ORIENTATION.toString();
		String code2 = QuestionNaireConstants.QUESTIONNAIRE_TYPE_ENUMS.QUES_GRADUATION.toString();
		if(DataUtil.isNotNull(typeCode) && typeCode.equals(code1)){
			vlaues.add(typeCode);
		}else if(DataUtil.isNotNull(typeCode) && typeCode.equals(code2)){
			vlaues.add(typeCode);
		}
		return  this.query(hql.toString(),vlaues.toArray());
	}
	
}
