package com.uws.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.IQuestionNaireDao;
import com.uws.common.service.IQuestionNaireService;
import com.uws.common.util.QuestionNaireConstants;
import com.uws.core.base.BaseServiceImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.DataUtil;
import com.uws.domain.question.QuestionAnswerBaseModel;
import com.uws.domain.question.QuestionAnswerDetailModel;
import com.uws.domain.question.QuestionInfoItemModel;
import com.uws.domain.question.QuestionInfoModel;
import com.uws.domain.question.QuestionItemOptionModel;
import com.uws.sys.model.Dic;

@Service("questionNaireService")
public class QuestionNaireServiceImpl extends BaseServiceImpl implements IQuestionNaireService {

	@Autowired
	private IQuestionNaireDao questionNaireDao;
	
	@Override
	public boolean isSubmitQuestionNaire(String respondent,String questionNaireId) {
		if(DataUtil.isNotNull(respondent) && DataUtil.isNotNull(questionNaireId)){
			return this.questionNaireDao.isSubmitQuestionNaire(respondent,questionNaireId);
		}
		return false;
	}

	@Override
	public QuestionAnswerBaseModel getQuesNaireBaseModel(String currentUserId,String questionNaireId) {
		return this.questionNaireDao.getQuesNaireBaseModel(currentUserId,questionNaireId);
	}
	
	@Override
	public QuestionInfoModel getQuestionNaireInfo(String questionNaireId) {
		return this.questionNaireDao.getQuestionNaireInfo(questionNaireId);
	}

	@Override
	public List<QuestionInfoModel> getNewStudentQuestionNaireInfo(String respondent) {
		return this.questionNaireDao.getNewStudentQuestionNaireInfo(respondent);
	}

	@Override
	public List<QuestionInfoModel> getOpenNaireList(){
		return this.questionNaireDao.getOpenNaireList();
	}
	
	public List<QuestionInfoModel> getQuestionNaireInfoList(){
		return this.questionNaireDao.getQuestionNaireInfoList();
	}
	
	@Override
	public List<QuestionInfoItemModel> getQuestionNaireSingleItemList(String questionNaireId) {
		return this.questionNaireDao.getQuestionNaireSingleItemList(questionNaireId);
	}

	@Override
	public List<QuestionInfoItemModel> getQuestionNaireMulItemList(String questionNaireId) {
		return this.questionNaireDao.getQuestionNaireMulItemList(questionNaireId);
	}

	@Override
	public List<QuestionInfoItemModel> getQuestionNaireSubItemList(String questionNaireId) {
		return this.questionNaireDao.getQuestionNaireSubItemList(questionNaireId);
	}

	@Override
	public List<QuestionItemOptionModel> getSingleQuestionOption(String respondent, String questionNaireId) {
		QuestionAnswerBaseModel qabm = this.questionNaireDao.getQuestionNaireBaseInfo(respondent,questionNaireId);
		//单选题选项
		List<QuestionItemOptionModel> singleOptionList = this.questionNaireDao.getSingleQuestionOption(questionNaireId);
		if(DataUtil.isNotNull(qabm)){//不是第一次答题
			//单选题答案
			List<QuestionAnswerDetailModel> singleAnswerList = 
					this.questionNaireDao.getSingleQuestionAnswer(respondent,questionNaireId);
			singleOptionList = this.formatChoiceQuestionInfo(singleAnswerList,singleOptionList);
		}
		return singleOptionList;
	}
	
	@Override
	public List<QuestionItemOptionModel> getMulQuestionOption(String respondent, String questionNaireId) {
		QuestionAnswerBaseModel qabm = this.questionNaireDao.getQuestionNaireBaseInfo(respondent,questionNaireId);
		//多选题选项
		List<QuestionItemOptionModel> mulOptionList = this.questionNaireDao.getMulQuestionOption(questionNaireId);
		if(DataUtil.isNotNull(qabm)){//不是第一次答题
			//多选题答案
			List<QuestionAnswerDetailModel> mulAnswerList = 
					this.questionNaireDao.getMulQuestionAnswer(respondent,questionNaireId);
			mulOptionList = this.formatChoiceQuestionInfo(mulAnswerList,mulOptionList);
		}
		return mulOptionList;
	}

	/**
	 * 问卷填写回写选择题列表
	 * @param answerList				选择题答案列表
	 * @param optionList				选择题选项列表
	 * @return										选择题回显列表
	 */
	private List<QuestionItemOptionModel> formatChoiceQuestionInfo(
			List<QuestionAnswerDetailModel> answerList,
			List<QuestionItemOptionModel> optionList) {
		List<QuestionItemOptionModel> returnList = new ArrayList<QuestionItemOptionModel>();
		for(QuestionItemOptionModel optionPo:optionList){
			//先移除list中的当前对象
			returnList.remove(optionPo);
			for(int i=0;i<answerList.size();i++){
				QuestionAnswerDetailModel answerPo = answerList.get(i);
				//选项答案ID
				String answerId = (DataUtil.isNotNull(answerPo.getItemOptionInfo()))?answerPo.getItemOptionInfo().getId():"";
				//选项备选答案ID
				String optionId = (DataUtil.isNotNull(optionPo.getId()))?optionPo.getId():"";
				if(DataUtil.isNotNull(answerId) && DataUtil.isNotNull(optionId) && answerId.equals(optionId)){
					//选中已选择的备选答案
					optionPo.setChecked(QuestionNaireConstants.INPUT_FLAG_ENUMS.CHECKED.toString());
				}
			}
			//重置list中的当前对象
			returnList.add(optionPo);
		}
		return returnList;
	}
	
	@Override
	public List<QuestionItemOptionModel> getAnswerQuestionOption(String respondent, String questionNaireId) {
		QuestionAnswerBaseModel qabm = this.questionNaireDao.getQuestionNaireBaseInfo(respondent,questionNaireId);
		//主观题选项
		List<QuestionItemOptionModel> subOptionList = this.questionNaireDao.getSubQuestionOption(questionNaireId);
		if(DataUtil.isNotNull(qabm)){//不是第一次答题
			//主观题答案
			List<QuestionAnswerDetailModel> subAnswerList = 
					this.questionNaireDao.getSubQuestionAnswer(respondent,questionNaireId);
			subOptionList = this.formatSubQuestionInfo(subAnswerList,subOptionList);
		}
		return subOptionList;
	}

	/**
	 * 问卷填写回显主观题答案
	 * @param subAnswerList			主观题答案列表
	 * @param subOptionList			主观题选项列表
	 * @return											主观题回显列表
	 */
	private List<QuestionItemOptionModel> formatSubQuestionInfo(
			List<QuestionAnswerDetailModel> subAnswerList,
			List<QuestionItemOptionModel> subOptionList) {
		List<QuestionItemOptionModel> returnList = new ArrayList<QuestionItemOptionModel>();
		for(QuestionItemOptionModel optionPo:subOptionList){
			for(int i=0;i<subAnswerList.size();i++){
				QuestionAnswerDetailModel answerPo = subAnswerList.get(i);
				//选项答案ID
				String answerId = (DataUtil.isNotNull(answerPo.getItemOptionInfo()))?answerPo.getItemOptionInfo().getId():"";
				//选项备选答案ID
				String optionId = (DataUtil.isNotNull(optionPo.getId()))?optionPo.getId():"";
				if(DataUtil.isNotNull(answerId) && DataUtil.isNotNull(optionId) && answerId.equals(optionId)){
					//回显答案
					optionPo.setAnswer(answerPo.getItemAnswer());
				}
			}
			returnList.add(optionPo);
		}
		return returnList;
	}

	@Override
	public void saveCurQuestionNaire(			
			QuestionAnswerBaseModel qabm,
			List<QuestionAnswerDetailModel> singleQadms,
			List<QuestionAnswerDetailModel> mulQadms,
			List<QuestionAnswerDetailModel> answerQadms){
		
		//保存当前答卷基本信息
			this.questionNaireDao.savePaper(qabm);
		//清空当前答卷详细信息
			this.clearPaperDetailInfo(qabm);
		//保存当前答卷信息
			this.questionNaireDao.saveQuestionNaire(singleQadms,mulQadms,answerQadms);
	}

	@Override
	public void submitCurQuestionNaire(
			QuestionAnswerBaseModel qabm,
			List<QuestionAnswerDetailModel> singleQadms,
			List<QuestionAnswerDetailModel> mulQadms,
			List<QuestionAnswerDetailModel> answerQadms){
		
			 //提交当前答卷基本信息
				this.questionNaireDao.submitPaper(qabm);
			//清空当前答卷详细信息
				this.clearPaperDetailInfo(qabm);
			//提交当前答卷信息
				this.questionNaireDao.submitQuestionNaire(singleQadms,mulQadms,answerQadms);
	}

	/**
	 *  清空当前答卷信息
	 * @param qabm			答卷基本信息
	 */
	private void clearPaperDetailInfo(QuestionAnswerBaseModel qabm) {
		if(DataUtil.isNotNull(qabm) && DataUtil.isNotNull(qabm.getQuestionInfo())){
			String questionNaireId = qabm.getQuestionInfo().getId();
			//清空答卷的详细信息
			this.questionNaireDao.deletePaperDetailInfo(qabm.getRespondent().getId(),questionNaireId);
		}
	}

	/**
	 * 描述信息:获取相关模块的问卷列表
	 * @param userId
	 * @param qabm
	 * @param flag
	 * @return
	 * 2015-11-5 下午2:12:29
	 */
	@Override
	public Page queryQuestionPage(String userId,QuestionAnswerBaseModel qabm,int pageNo,int pageSize,boolean flag)
    {
    	return questionNaireDao.queryQuestionPage(userId,qabm, pageNo, pageSize,flag);
    }
	
	/**
	 * 描述信息: Question Selected Page
	 * @param pageNum
	 * @param pageSize
	 * @param currentUserId
	 * @return
	 * 2015-11-5 下午3:17:28
	 */
	@Override
	public Page queryUserQuesInfo(int pageNum,int pageSize,String currentUserId) {
		return questionNaireDao.queryUserQuesInfo(pageNum,pageSize,currentUserId);
	}
}
