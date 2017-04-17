package com.uws.common.service;

import java.util.List;

import com.uws.core.base.IBaseService;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.question.QuestionAnswerBaseModel;
import com.uws.domain.question.QuestionAnswerDetailModel;
import com.uws.domain.question.QuestionInfoItemModel;
import com.uws.domain.question.QuestionInfoModel;
import com.uws.domain.question.QuestionItemOptionModel;

/**
 *	问卷填写子模块对外公用接口
 */
public interface IQuestionNaireService extends IBaseService {
	
	/**
	 * 判断答题人待回答的问卷是否已提交
	 * @param respondent							答题人
	 * @param questionNaireId				问卷Id
	 * @return													[true：已提交，false：未提交]
	 */
	public boolean isSubmitQuestionNaire(String respondent,String questionNaireId);
	
	/**
	 * 获取当前登录人保存的答卷
	 * @param currentUserId					答题人
	 * @param questionNaireId				问卷Id
	 * @return													当前登陆人保存的问卷
	 */
	public QuestionAnswerBaseModel getQuesNaireBaseModel(String currentUserId,String questionNaireId);
	
	/**
	 * 获取问卷信息
	 * @param questionNaireId				问卷主键ID
	 * @return													问卷对象信息
	 */
	public QuestionInfoModel getQuestionNaireInfo(String questionNaireId);
	
	/**
	 * 获取新生待答的问卷列表
	 * @param respondent							答题人
	 * @return													问卷列表
	 */
	public List<QuestionInfoModel> getNewStudentQuestionNaireInfo(String respondent);
	
	/**
	 * 获取公开的问卷
	 * @param respondent		答题人
	 * @return								问卷列表
	 */
	public List<QuestionInfoModel> getOpenNaireList();
	
	/**
	 * 获取发布的问卷【临时方法】
	 * @return
	 */
	public List<QuestionInfoModel> getQuestionNaireInfoList();
	
	/**
	 * 获取问卷下的单选题目列表
	 * @param questionNaireId				问卷id
	 * @return													题目列表
	 */
	public List<QuestionInfoItemModel> getQuestionNaireSingleItemList(String questionNaireId);
	
	/**
	 * 获取问卷下的多选题目列表
	 * @param questionNaireId				问卷id
	 * @return													题目列表
	 */
	public List<QuestionInfoItemModel> getQuestionNaireMulItemList(String questionNaireId);

	/**
	 * 获取问卷下的问答题目列表
	 * @param questionNaireId				问卷id
	 * @return													题目列表
	 */
	public List<QuestionInfoItemModel> getQuestionNaireSubItemList(String questionNaireId);
	
	/**
	 * 获取答卷单选题列表
	 * @param respondent							答题人
	 * @param questionNaireId				问卷主键ID
	 * @return													单选题列表
	 */
	public List<QuestionItemOptionModel> getSingleQuestionOption(String respondent,String questionNaireId);
	
	/**
	 * 获取答卷多选题列表
	 * @param respondent							答题人
	 * @param questionNaireId				问卷主键ID
	 * @return													多选题列表
	 */
	public List<QuestionItemOptionModel> getMulQuestionOption(String respondent,String questionNaireId);
	
	/**
	 * 获取答卷问答题列表
	 * @param respondent							答题人
	 * @param questionNaireId				问卷主键ID
	 * @return													问答题列表
	 */
	public List<QuestionItemOptionModel> getAnswerQuestionOption(String respondent,String questionNaireId);
	
	/**
	 * 保存当前问卷
	 * @param qabm									问卷基本信息
	 * @param singleQadms					单选题详细信息
	 * @param mulQadms						多选题详细信息
	 * @param answerQadms				问答题详细信息
	 */
	public void saveCurQuestionNaire(
			QuestionAnswerBaseModel qabm,
			List<QuestionAnswerDetailModel> singleQadms,
			List<QuestionAnswerDetailModel> mulQadms,
			List<QuestionAnswerDetailModel> answerQadms);
	
	/**
	 * 提交当前问卷
	 * @param qabm									问卷基本信息
	 * @param singleQadms					单选题详细信息
	 * @param mulQadms						多选题详细信息
	 * @param answerQadms				问答题详细信息
	 */
	public void submitCurQuestionNaire(
			QuestionAnswerBaseModel qabm,
			List<QuestionAnswerDetailModel> singleQadms,
			List<QuestionAnswerDetailModel> mulQadms,
			List<QuestionAnswerDetailModel> answerQadms);
	
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
	 * @param pageNum					分页页码
	 * @param pageSize					分页大小
	 * @param currentUserId		当前用户id
	 * @return										问卷分页集合
	 */
	public Page queryUserQuesInfo(int pageNum,int pageSize,String currentUserId);
	
	
}
