package com.uws.common.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.question.QuestionAnswerBaseModel;
import com.uws.domain.question.QuestionAnswerDetailModel;
import com.uws.domain.question.QuestionInfoItemModel;
import com.uws.domain.question.QuestionInfoModel;
import com.uws.domain.question.QuestionItemOptionModel;

public interface IQuestionNaireDao extends IBaseDao {

	/**
	 * 判断答题人待回答的问卷是否已提交
	 * @param respondent							答题人
	 * @param questionNaireId				问卷Id
	 * @return													[true：已提交，false：未提交]
	 */
	public boolean isSubmitQuestionNaire(String respondent, String questionNaireId);

	/**
	 * 获取当前登录人保存的答卷
	 * @param currentUserId					答题人
	 * @param questionNaireId				问卷Id
	 * @return													当前登陆人保存的问卷
	 */
	public QuestionAnswerBaseModel getQuesNaireBaseModel(String currentUserId,String questionNaireId);

	
	/**
	 * 获取答题人的答题记录
	 * @param respondent							答题人
	 * @param questionNaireId				问卷Id
	 * @return													问卷答题记录
	 */
	public QuestionAnswerBaseModel getQuestionNaireBaseInfo(String respondent,String questionNaireId);
	
	/**
	 * 获取单个问卷
	 * @param questionNaireId				问卷id
	 * @return													问卷对象
	 */
	public QuestionInfoModel getQuestionNaireInfo(String questionNaireId);
	
	/**
	 * 临时获取启用问卷的方法
	 * @return
	 */
	public List<QuestionInfoModel> getQuestionNaireInfoList();
	
	/**
	 * 获取新生问卷信息
	 * @param questionNaireId				问卷主键ID
	 * @return													问卷对象信息
	 */
	public List<QuestionInfoModel> getNewStudentQuestionNaireInfo(String respondent);
	
	/**
	 * 获取公开的问卷
	 * @param respondent		答题人
	 * @return
	 */
	public List<QuestionInfoModel> getOpenNaireList();
	
	/**
	 * 获取问卷下的单选问题列表
	 * @param questionNaireId				问卷id
	 * @return													问题列表
	 */
	public List<QuestionInfoItemModel> getQuestionNaireSingleItemList(String questionNaireId);
	
	/**
	 * 获取问卷下的多选问题列表
	 * @param questionNaireId				问卷id
	 * @return													问题列表
	 */
	public List<QuestionInfoItemModel> getQuestionNaireMulItemList(String questionNaireId);
	
	/**
	 * 获取问卷下的问答题列表
	 * @param questionNaireId				问卷id
	 * @return													问题列表
	 */
	public List<QuestionInfoItemModel> getQuestionNaireSubItemList(String questionNaireId);
	
	/**
	 * 获取当前问卷的单选题选项列表
	 * @param questionNaireId				问卷ID
	 * @return													问卷单选题选项列表
	 */
	public List<QuestionItemOptionModel> getSingleQuestionOption(String questionNaireId);
	
	/**
	 * 获取当前问卷单选题答案列表				
	 * @param respondent							答题人
	 * @param questionNaireId				问卷ID
	 * @return													问卷单选题答案列表
	 */
	public List<QuestionAnswerDetailModel> getSingleQuestionAnswer(String respondent, String questionNaireId);
	
	/**
	 * 获取当前问卷多选题选项列表				
	 * @param respondent							答题人
	 * @param questionNaireId				问卷ID
	 * @return													问卷多选题选项列表
	 */
	public List<QuestionItemOptionModel> getMulQuestionOption(String questionNaireId);
	
	/**
	 * 获取当前问卷多选题答案列表
	 * @param respondent							答题人
	 * @param questionNaireId				问卷ID
	 * @return													问卷多选题答案列表
	 */
	public List<QuestionAnswerDetailModel> getMulQuestionAnswer(String respondent, String questionNaireId);
	
	/**
	 * 获取当前问卷主观题选项列表				
	 * @param respondent							答题人
	 * @param questionNaireId				问卷ID
	 * @return													问卷主观题选项列表
	 */
	public List<QuestionItemOptionModel> getSubQuestionOption(String questionNaireId);
	
	/**
	 * 获取当前问卷主观题答案列表
	 * @param respondent							答题人
	 * @param questionNaireId				问卷ID
	 * @return													问卷主观题答案列表
	 */
	public List<QuestionAnswerDetailModel> getSubQuestionAnswer(String respondent, String questionNaireId);

	/**
	 * 获取答题人回答的某个问卷
	 * @param respondent							答题人
	 * @param questionNaireId				问卷ID
	 * @return													问卷对象
	 */
	public QuestionAnswerBaseModel getQuestionNaire(String respondent,String questionNaireId);

	/**
	 * 保存问卷答案
	 * @param singleQadms						单选题信息
	 * @param mulQadms							多选题信息
	 * @param answerQadms					问答题信息
	 */
	public void saveQuestionNaire(
			List<QuestionAnswerDetailModel> singleQadms, 
			List<QuestionAnswerDetailModel> mulQadms,
			List<QuestionAnswerDetailModel> answerQadms);

	/**
	 * 提交问卷答案
	 * @param singleQadms						单选题信息
	 * @param mulQadms							多选题信息
	 * @param answerQadms					问答题信息
	 */
	public void submitQuestionNaire(
			List<QuestionAnswerDetailModel> singleQadms, 
			List<QuestionAnswerDetailModel> mulQadms,
			List<QuestionAnswerDetailModel> answerQadms);
	
	/**
	 * 保存答卷信息
	 * @param qabm			答卷基本信息对象
	 */
	public void savePaper(QuestionAnswerBaseModel qabm);
	
	/**
	 * 提交当前答卷的新增操作
	 * @param qabm
	 */
	public void submitPaper(QuestionAnswerBaseModel qabm);

	/**
	 * 删除答卷的详细信息
	 * @param respondent				答题人
	 * @param questionNaireId	问卷id
	 */
	public void deletePaperDetailInfo(String respondent,String questionNaireId);

	/**
	 * 删除答卷的基本信息
	 * @param respondent				答题人
	 * @param questionNaireId	问卷id
	 */
	public void deletePaperBaseInfo(String respondent,String questionNaireId);
	
	/**
	 * 
	 * @Title: queryQuestionPage
	 * @Description: 获取相关模块的问卷列表
	 * @param userId
	 * @param qabm
	 * @param flag
	 * @return
	 * @throws
	 * 注释： flag== true 包括公共问卷， flag== false:不包括公共问卷
	 */
	public Page queryQuestionPage(String userId,QuestionAnswerBaseModel qabm,int pageNo,int pageSize,boolean flag);
	
	/**
	 * 查询调查问卷信息
	 * @param pageNum
	 * @param pageSize
	 * @param currentUserId		当前用户id
	 * @return
	 */
	public Page queryUserQuesInfo(int pageNum,int pageSize,String currentUserId);

}
