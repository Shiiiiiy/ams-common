package com.uws.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uws.common.service.IQuestionNaireService;
import com.uws.common.util.QuestionNaireConstants;
import com.uws.common.util.QuestionNaireUtil;
import com.uws.core.base.BaseController;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.session.SessionFactory;
import com.uws.core.session.SessionUtil;
import com.uws.core.util.DataUtil;
import com.uws.domain.question.QuestionAnswerBaseModel;
import com.uws.domain.question.QuestionAnswerDetailModel;
import com.uws.domain.question.QuestionInfoItemModel;
import com.uws.domain.question.QuestionInfoModel;
import com.uws.domain.question.QuestionItemOptionModel;
import com.uws.log.Logger;
import com.uws.log.LoggerFactory;
import com.uws.sys.model.Dic;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;

/**
 * 
* @ClassName: QuestionCommonController 
* @Description: 问卷调查的公用 
* @author 联合永道
* @date 2015-11-5 下午1:47:59 
*
 */
@Controller("com.uws.common.controller.QuestionCommonController")
public class QuestionCommonController extends BaseController
{
	@Autowired
	private IQuestionNaireService questionNaireService;
	
	private DicUtil dicUtil = DicFactory.getDicUtil();
	private SessionUtil sessionUtil = SessionFactory.getSession("/common/question/");
	private Logger logger = new LoggerFactory(QuestionCommonController.class);
	
	/**
	 * 
	 * @Title: queryQuestionPage
	 * @Description: 调查问卷 查询分页
	 * @param request
	 * @param response
	 * @param model
	 * @param qabm
	 * @return
	 * @throws
	 */
	@RequestMapping("/common/question{questionType}/opt-query/questionPage")
	public String queryQuestionPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,QuestionAnswerBaseModel qabm,@PathVariable String questionType)
	{
		int pageNo = request.getParameter("pageNo")!=null?Integer.parseInt(request.getParameter("pageNo")):1;
		List<Dic> paperTypeList = dicUtil.getDicInfoList("QUESINFO_TYPE");
		List<Dic> paperStatusList = dicUtil.getDicInfoList("PAPER_STATUS");//问卷状态
		List<Dic> answerStatusList = dicUtil.getDicInfoList("ANSWER_STATUS");//答题状态
		logger.debug("封装问卷类型条件 : " + questionType);
		
		if(!StringUtils.isEmpty(questionType))
		{
			Dic dic = dicUtil.getDicInfo("QUESINFO_TYPE",QuestionNaireUtil.getTypeDicCode(questionType));
			qabm.setQuesnaireType(dic);
		}
		
		Page page = this.questionNaireService.queryQuestionPage(sessionUtil.getCurrentUserId(),qabm, pageNo, Page.DEFAULT_PAGE_SIZE,false);
		model.addAttribute("page", page);
		model.addAttribute("qabm", qabm);
		model.addAttribute("questionInfo", new QuestionInfoModel());//供页面回显调用
		model.addAttribute("typeList", paperTypeList);
		model.addAttribute("answerStatusList", answerStatusList);
		model.addAttribute("paperStatusList", paperStatusList);
		model.addAttribute("questionType", questionType);
		
		return "/common/question/paperSelectedList";
	}
	
	/**
	 * 
	 * @Title: quesSelectQuery
	 * @Description: 问卷选择 
	 * @param request
	 * @param response
	 * @param model
	 * @param questionNairePo
	 * @return
	 * @throws
	 */
	@RequestMapping({"/common/question/nsm/quesSelectQuery"})
	public String quesSelectQuery(HttpServletRequest request,HttpServletResponse response,ModelMap model,QuestionInfoModel questionNairePo){
		
		int pageNo = request.getParameter("pageNo")!=null?Integer.parseInt(request.getParameter("pageNo")):1;
		List<Dic> paperTypeList = dicUtil.getDicInfoList("QUESINFO_TYPE");
		List<Dic> paperStatusList = dicUtil.getDicInfoList("PAPER_STATUS");//问卷状态
		questionNairePo.setStatusDic(dicUtil.getDicInfo("PAPER_STATUS", "STATUS_ENABLE"));
		Page page = questionNaireService.queryUserQuesInfo(pageNo, Page.DEFAULT_PAGE_SIZE,this.sessionUtil.getCurrentUserId());
		model.addAttribute("page", page);
		model.addAttribute("questionInfo", questionNairePo);
		model.addAttribute("typeList", paperTypeList);
		model.addAttribute("paperStatusList", paperStatusList);
		model.addAttribute("curpageNo", pageNo);
		model.addAttribute("paperSize", page.getTotalCount());
		return "/common/question/paperSelectList";
	}
	
	
	/**
	 * 查看当前问卷				
	 * @param request							当次请求
	 * @param response						当次响应
	 * @param model							页面模型		
	 * @param paperId						问卷id	
	 * @return											答题视图	
	 */
	@RequestMapping({"/common/question/view/viewCurPaper"})
	public String viewCurPaper(HttpServletRequest request,HttpServletResponse response,ModelMap model,String paperId){
		
		QuestionInfoModel qim = this.questionNaireService.getQuestionNaireInfo(paperId);
		List<QuestionInfoItemModel> singleItemList = null;
		List<QuestionInfoItemModel> mulItemList = null;
		List<QuestionInfoItemModel> subItemList = null;
		List<QuestionItemOptionModel>  singleQuesOptionList = null;
		List<QuestionItemOptionModel>  mulQuesOptionList  = null;
		List<QuestionItemOptionModel>  subQuesAnswerList = null; 
		if(DataUtil.isNotNull(qim) && DataUtil.isNotNull(qim.getId())){
			//获取问卷主键
			String questionNaireId = qim.getId();
			//获取问卷单选题列表
			singleItemList = this.questionNaireService.getQuestionNaireSingleItemList(questionNaireId);
			//获取问卷多选题列表
			mulItemList = this.questionNaireService.getQuestionNaireMulItemList(questionNaireId);
			//获取问卷问答题列表
			subItemList = this.questionNaireService.getQuestionNaireSubItemList(questionNaireId);
			//获取单选题答题列表
			singleQuesOptionList = this.questionNaireService.getSingleQuestionOption(this.sessionUtil.getCurrentUserId(), questionNaireId);
			//获取多选题答题列表
			mulQuesOptionList = this.questionNaireService.getMulQuestionOption(this.sessionUtil.getCurrentUserId(), questionNaireId);
			//获取问答题答题列表
			subQuesAnswerList = this.questionNaireService.getAnswerQuestionOption(this.sessionUtil.getCurrentUserId(), questionNaireId);
		}
		
		model.addAttribute("qim", qim);
		model.addAttribute("singleItemList", singleItemList);
		model.addAttribute("mulItemList", mulItemList);
		model.addAttribute("subItemList", subItemList);
		model.addAttribute("singleQuesOptionList", singleQuesOptionList);
		model.addAttribute("mulQuesOptionList", mulQuesOptionList);
		model.addAttribute("subQuesAnswerList", subQuesAnswerList);
		model.addAttribute("splitFlag", QuestionNaireConstants.AMS_SPLIT_FLAG_QUESTIONNAIRE);
		return "/common/question/paperView";
	}
	
	/**
	 * 回答当前问卷				
	 * @param request							当次请求
	 * @param response						当次响应
	 * @param model							页面模型		
	 * @param paperId						问卷id	
	 * @return											答题视图	
	 */
	@RequestMapping({"/common/question/opt-edit/paperEdit"})
	public String editCurPaper(HttpServletRequest request,HttpServletResponse response,ModelMap model,String paperId,String questionType){
		
		QuestionInfoModel qim = this.questionNaireService.getQuestionNaireInfo(paperId);
		List<QuestionInfoItemModel> singleItemList = null;
		List<QuestionInfoItemModel> mulItemList = null;
		List<QuestionInfoItemModel> subItemList = null;
		List<QuestionItemOptionModel>  singleQuesOptionList = null;
		List<QuestionItemOptionModel>  mulQuesOptionList  = null;
		List<QuestionItemOptionModel>  subQuesAnswerList = null; 
		if(DataUtil.isNotNull(qim) && DataUtil.isNotNull(qim.getId())){
			//获取问卷主键
			String questionNaireId = qim.getId();
			//获取问卷单选题列表
			singleItemList = this.questionNaireService.getQuestionNaireSingleItemList(questionNaireId);
			//获取问卷多选题列表
			mulItemList = this.questionNaireService.getQuestionNaireMulItemList(questionNaireId);
			//获取问卷问答题列表
			subItemList = this.questionNaireService.getQuestionNaireSubItemList(questionNaireId);
			//获取单选题答题列表
			singleQuesOptionList = this.questionNaireService.getSingleQuestionOption(this.sessionUtil.getCurrentUserId(), questionNaireId);
			//获取多选题答题列表
			mulQuesOptionList = this.questionNaireService.getMulQuestionOption(this.sessionUtil.getCurrentUserId(), questionNaireId);
			//获取问答题答题列表
			subQuesAnswerList = this.questionNaireService.getAnswerQuestionOption(this.sessionUtil.getCurrentUserId(), questionNaireId);
		}
		
		model.addAttribute("qim", qim);
		model.addAttribute("singleItemList", singleItemList);
		model.addAttribute("mulItemList", mulItemList);
		model.addAttribute("subItemList", subItemList);
		model.addAttribute("singleQuesOptionList", singleQuesOptionList);
		model.addAttribute("mulQuesOptionList", mulQuesOptionList);
		model.addAttribute("questionType", questionType);
		model.addAttribute("subQuesAnswerList", subQuesAnswerList);
		model.addAttribute("splitFlag", QuestionNaireConstants.AMS_SPLIT_FLAG_QUESTIONNAIRE);
		return "/common/question/paperEdit";
	}
	
	/**
	 * 保存当前问卷
	 * @param request							当次请求
	 * @param response						当次响应
	 * @param model							页面模型
	 * @param questionNairePo		问卷对象
	 * @param singleOption				单选选中选项
	 * @param mulOption					多选选中选项
	 * @param singleQadms				单选题答案数组
	 * @param mulQadms					多选题答案数组
	 * @param answerQadms			问答题目信息数组
	 * @param subItemAreas				问答题答案数组
	 * @return											答题列表
	 */
	@RequestMapping({"/common/question/opt-edit/saveCurPaper"})
	public String saveCurPaper(HttpServletRequest request,HttpServletResponse response,
			ModelMap model,QuestionInfoModel questionNairePo,
			String [] singleOption,String [] mulOption,
			String [] singleQadms,String [] mulQadms,String [] answerQadms,String [] subItemAreas,String questionType){
		
		//当前用户ID
		String userId = this.sessionUtil.getCurrentUserId();
		//答卷基本信息
		QuestionAnswerBaseModel qabm = this.getQuestionNaireBaseInfo(questionNairePo);
		//答卷单选题信息
		List<QuestionAnswerDetailModel> singleQadmList = QuestionNaireUtil.getSingleQuestionNaireDetailInfo(singleOption,singleQadms,qabm,userId);
		//答卷多选题信息
		List<QuestionAnswerDetailModel> mulQadmList = QuestionNaireUtil.getMulQuestionNaireDetailInfo(mulOption,mulQadms,qabm,userId);
		//答卷问答题信息
		List<QuestionAnswerDetailModel> answerQadmList = QuestionNaireUtil.getAnswerQuestionNaireDetailInfo(answerQadms,qabm,subItemAreas,userId);
		//保存当前问卷信息
		this.questionNaireService.saveCurQuestionNaire(qabm, singleQadmList, mulQadmList, answerQadmList);
		
		return "redirect:/common/question"+questionType+"/opt-query/questionPage.do";
	}

	/**
	 * 提交当前问卷	
	 * @param request							当次请求
	 * @param response						当次响应
	 * @param model							页面模型
	 * @param questionNairePo		问卷对象
	 * @param singleOption				单选选中选项
	 * @param mulOption					多选选中选项
	 * @param singleQadms				单选题答案数组
	 * @param mulQadms					多选题答案数组
	 * @param answerQadms			问答题目信息数组
	 * @param subItemAreas				问答题答案数组
	 * @return											答题列表
	 */
	@RequestMapping({"/common/question/opt-edit/submitCurPaper"})
	public String submitCurPaper(HttpServletRequest request,HttpServletResponse response,
			ModelMap model,QuestionInfoModel questionNairePo,
			String [] singleOption,String [] mulOption,
			String [] singleQadms,String [] mulQadms,String [] answerQadms,String [] subItemAreas,String questionType){
		
		//当前用户ID
		String userId = this.sessionUtil.getCurrentUserId();
		//答卷基本信息
		QuestionAnswerBaseModel qabm = this.getQuestionNaireBaseInfo(questionNairePo);
		//答卷单选题信息
		List<QuestionAnswerDetailModel> singleQadmList = QuestionNaireUtil.getSingleQuestionNaireDetailInfo(singleOption,singleQadms,qabm,userId);
		//答卷多选题信息
		List<QuestionAnswerDetailModel> mulQadmList = QuestionNaireUtil.getMulQuestionNaireDetailInfo(mulOption,mulQadms,qabm,userId);
		//答卷问答题信息
		List<QuestionAnswerDetailModel> answerQadmList = QuestionNaireUtil.getAnswerQuestionNaireDetailInfo(answerQadms,qabm,subItemAreas,userId);
		//提交当前问卷信息
		this.questionNaireService.submitCurQuestionNaire(qabm, singleQadmList, mulQadmList, answerQadmList);
		
		return "redirect:/common/question"+questionType+"/opt-query/questionPage.do";
	}
	

	/**
	 * 获取答卷的基本信息
	 * @param questionNairePo	问卷对象
	 * @return										答卷基本信息
	 */
	private QuestionAnswerBaseModel getQuestionNaireBaseInfo(QuestionInfoModel questionNairePo) {

		String userId = this.sessionUtil.getCurrentUserId();
		QuestionAnswerBaseModel qabm = 
				this.questionNaireService.getQuesNaireBaseModel(userId,questionNairePo.getQuestionNaireId());
		if(!DataUtil.isNotNull(qabm)){
			qabm = QuestionNaireUtil.formateQuestionNaireBaseInfo(questionNairePo,userId);
		}
		return qabm;
	}
}
