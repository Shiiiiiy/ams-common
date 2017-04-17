package com.uws.common.util;

import java.util.ArrayList;
import java.util.List;
import com.uws.core.util.DataUtil;
import com.uws.core.util.DateUtil;
import com.uws.domain.question.QuestionAnswerBaseModel;
import com.uws.domain.question.QuestionAnswerDetailModel;
import com.uws.domain.question.QuestionInfoItemModel;
import com.uws.domain.question.QuestionInfoModel;
import com.uws.domain.question.QuestionItemOptionModel;
import com.uws.sys.model.Dic;
import com.uws.user.model.User;

/**
 * 收集问卷答案的通过方法
 */
public class QuestionNaireUtil {
	
	/**
	 * 根据问卷信息封装答卷的基本信息
	 * @param questionNairePo			问卷信息
	 * @param userId  								用户Id
	 * @return												答卷基本信息
	 */
	public static QuestionAnswerBaseModel formateQuestionNaireBaseInfo(QuestionInfoModel questionNairePo,String userId) {
		
		QuestionAnswerBaseModel qabm = new QuestionAnswerBaseModel();
		//设置答题人
		User respondent = new User();
		respondent.setId(userId);
		qabm.setRespondent(respondent);
		//设置问卷信息
		qabm.setQuestionInfo(questionNairePo);
		//设置创建时间
		qabm.setCreateTime(AmsDateUtil.toTime(DateUtil.getCurTime()));
		//设置修改时间
		qabm.setUpdateTime(AmsDateUtil.toTime(DateUtil.getCurTime()));
		//设置问卷类型
		qabm.setQuesnaireType(questionNairePo.getTypeDic());
		//设置问卷状态
		qabm.setAnswerStatus(QuestionNaireConstants.ANSWER_STATUS_UNCOMMITED);
		return qabm;
	}

	/**
	 * 获取单选题的答案详情
	 * @param singleOption		单选选中选项列表
	 * @param singleQadms		单选题答案列表
	 * @param qabm						答卷基础信息
	 * @param userId  					用户Id
	 * @return									处理后答案列表
	 */
	public static List<QuestionAnswerDetailModel> getSingleQuestionNaireDetailInfo(
			String[] singleOption,String[] singleQadms,QuestionAnswerBaseModel qabm,String userId) {
		
		List<QuestionAnswerDetailModel> singleQadmList = new ArrayList<QuestionAnswerDetailModel>();
		if(DataUtil.isNotNull(singleQadms)){
			//封装单选题答案列表
			for(int i=0;i<singleQadms.length;i++){
				String qiim = singleQadms[i];
				String checkFlag = singleOption[i];
				//封装单选题答案对象
				String answerArray [] = qiim.split(QuestionNaireConstants.AMS_SPLIT_FLAG_QUESTIONNAIRE);
				QuestionAnswerDetailModel qadm = formateQuesionNaireDetailInfo(checkFlag,null,answerArray,qabm,userId);
				singleQadmList.add(qadm);
			}
		}
		return singleQadmList;
	}

	/**
	 * 获取多选题的答案详情
	 * @param mulOption	多选选中选项列表
	 * @param mulQadms	多选题答案列表
	 * @param qabm				答卷基础信息
	 * @param userId  			用户Id
	 * @return							处理后答案列表
	 */
	public static List<QuestionAnswerDetailModel> getMulQuestionNaireDetailInfo(
			String[] mulOption,String[] mulQadms,QuestionAnswerBaseModel qabm,String userId) {
		
		List<QuestionAnswerDetailModel> mulQadmList = new ArrayList<QuestionAnswerDetailModel>();
		if(DataUtil.isNotNull(mulQadms)){
			//封装多选题答案列表
			for(int i=0;i<mulQadms.length;i++){
				String qiim = mulQadms[i];
				String checkFlag = mulOption[i];
				String answerArray [] = qiim.split(QuestionNaireConstants.AMS_SPLIT_FLAG_QUESTIONNAIRE);
				QuestionAnswerDetailModel qadm = formateQuesionNaireDetailInfo(checkFlag,null,answerArray,qabm,userId);
				mulQadmList.add(qadm);
			}
		}
		return mulQadmList;
	}
	
	/**
	 * 获取问答题的答案详情
	 * @param answerQadms			问答题目列表
	 * @param qabm								答卷基础信息
	 * @param subItemAreas				问答题答案数组
	 * @param userId  							用户Id
	 * @return											处理后答案列表
	 */
	public static List<QuestionAnswerDetailModel> getAnswerQuestionNaireDetailInfo(
			String[] answerQadms,QuestionAnswerBaseModel qabm,String [] subItemAreas,String userId) {
		
		List<QuestionAnswerDetailModel> answerQadmList = new ArrayList<QuestionAnswerDetailModel>();
		if(DataUtil.isNotNull(answerQadms)){
			//封装问答题答案列表
			for(int i=0;i<answerQadms.length;i++){
				String qiim = answerQadms[i];
				String subAnswer = (DataUtil.isNotNull(subItemAreas))?subItemAreas[i].trim():"";
				String answerArray [] = qiim.split(QuestionNaireConstants.AMS_SPLIT_FLAG_QUESTIONNAIRE);
				QuestionAnswerDetailModel qadm = formateQuesionNaireDetailInfo(null,subAnswer,answerArray,qabm,userId);
				answerQadmList.add(qadm);
			}
		}
		return answerQadmList;
	}

	/**
	 * 封装问卷答案详细信息
	 * @param checkFlag		 选择题选项
	 * @param subAnswer  主观题答案
	 * @param answers	     问答题目信息数组
	 * @param qabm				 问卷基本信息
	 * @param userId  			 用户Id 
	 * @return							 答卷详细信息
	 */
	public static QuestionAnswerDetailModel formateQuesionNaireDetailInfo(
			String checkFlag,String subAnswer,String []answers,QuestionAnswerBaseModel qabm,String userId) {
		
		QuestionAnswerDetailModel qadm = new QuestionAnswerDetailModel();
		
		//封装答题人信息
		User respondent = new User();
		respondent.setId(userId);
		qadm.setRespondent(respondent);
		
		//封装答题基础信息
		qadm.setAnswerBaseInfo(qabm);
		
		//封装问卷信息
		if(DataUtil.isNotNull(answers) && answers.length>=1 && DataUtil.isNotNull(answers[0])){
			QuestionInfoModel qimPo = new QuestionInfoModel();
			qimPo.setId(answers[0]);
			qadm.setQuestionInfo(qimPo);
		}
		
		//封装问卷-问题ID
		if(DataUtil.isNotNull(answers) && answers.length>=2 &&  DataUtil.isNotNull(answers[1])){
			QuestionInfoItemModel paperItemInfo = new QuestionInfoItemModel();
			paperItemInfo.setId(answers[1]);
			qadm.setPaperItemInfo(paperItemInfo);
		}
		
		//封装问题类型
		if(DataUtil.isNotNull(answers) && answers.length>=3  &&  DataUtil.isNotNull(answers[2])){
			Dic itemType = new Dic();
			itemType.setId(answers[2]);
			qadm.setItemType(itemType);
		}
		
		//封装问题名称
		if(DataUtil.isNotNull(answers) && answers.length>=4 &&  DataUtil.isNotNull(answers[3])){
			qadm.setItemName(answers[3]);
		}
		
		//封装问题选项信息
		if(DataUtil.isNotNull(answers) && answers.length>=5 &&  DataUtil.isNotNull(answers[4])){
			QuestionItemOptionModel qiom = new QuestionItemOptionModel();
			qiom.setId(answers[4]);
			qadm.setItemOptionInfo(qiom);
		}
		
		//封装选项序号
		if(DataUtil.isNotNull(answers) && answers.length>=6 &&  DataUtil.isNotNull(answers[5])){
			qadm.setOptionSeq(Integer.parseInt(answers[5]));
		}
		
		//封装选项编号
		if(DataUtil.isNotNull(answers) && answers.length>=7 &&  DataUtil.isNotNull(answers[6])){
			qadm.setOptionCode(answers[6]);
		}
		
		//封装选项名称
		if(DataUtil.isNotNull(answers) && answers.length>=8 &&  DataUtil.isNotNull(answers[7])){
			qadm.setOptionName(answers[7]);
		}
		
		//封装主观题答案
		if(DataUtil.isNotNull(answers) && answers.length>=9 &&  DataUtil.isNotNull(answers[8])){
			qadm.setItemAnswer(subAnswer);
		}
		
		//封装创建时间
		qadm.setCreateTime(DateUtil.getDate());
		
		//封装更新时间
		qadm.setUpdateTime(DateUtil.getDate());
		
		//封装问卷类型
		if(DataUtil.isNotNull(answers) && answers.length>=10 &&  DataUtil.isNotNull(answers[9])){
			qadm.setQuesnaireType(answers[9]);
		}
		
		//封装问卷-问题序号
		if(DataUtil.isNotNull(answers) && answers.length>=11 &&  DataUtil.isNotNull(answers[10])){
			qadm.setItemSeq(Integer.parseInt(answers[10]));
		}
		
		//封装题型序号
		if(DataUtil.isNotNull(answers) && answers.length>=12 && DataUtil.isNotNull(answers[11])){
			qadm.setItemTypeSeq(Integer.parseInt(answers[11]));
		}
		
		//封装选中的答案标志
		String checked_true = QuestionNaireConstants.INPUT_FLAG_ENUMS.TRUE.toString();
		if(DataUtil.isNotNull(checkFlag) && checkFlag.equalsIgnoreCase(checked_true)){
			qadm.setChecked(QuestionNaireConstants.INPUT_FLAG_ENUMS.CHECKED.toString());
		}
		
		return qadm;
	}
	
	/**
	 * 
	 * @Title: getTypeDic
	 * @Description: 问卷类型数据字典获取返回
	 * @param typeStr
	 * @return
	 * @throws
	 */
	public static String getTypeDicCode(String typeStr)
	{
		if("job".equalsIgnoreCase(typeStr))
			return  QuestionNaireConstants.QUESTIONNAIRE_TYPE_ENUMS.QUES_GRADUATION.toString();
		return "";
	}
	

}
