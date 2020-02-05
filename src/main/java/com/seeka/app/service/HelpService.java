package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.HelpAnswer;
import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;
import com.seeka.app.dao.IHelpDAO;
import com.seeka.app.dto.HelpAnswerDto;
import com.seeka.app.dto.HelpCategoryDto;
import com.seeka.app.dto.HelpDto;
import com.seeka.app.dto.HelpSubCategoryDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.enumeration.HelpEnum;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional
public class HelpService implements IHelpService {

	List<String> stopWordsForRelatedQuestions = Arrays.asList(new String[] { "a", "able", "about", "above", "abroad", "abst", "accordance", "according",
			"accordingly", "across", "act", "actually", "added", "adj", "adopted", "affected", "affecting", "affects", "after", "afterwards", "again",
			"against", "ago", "ah", "ahead", "ain't", "all", "allow", "allows", "almost", "alone", "along", "alongside", "already", "also", "although",
			"always", "am", "amid", "amidst", "among", "amongst", "amoungst", "amount", "an", "and", "announce", "another", "any", "anybody", "anyhow",
			"anymore", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "apparently", "appear", "appreciate", "appropriate", "approximately",
			"are", "aren", "arent", "aren't", "arise", "around", "as", "a's", "aside", "ask", "asking", "associated", "at", "auth", "available", "away",
			"awfully", "b", "back", "backward", "backwards", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand",
			"begin", "beginning", "beginnings", "begins", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond",
			"bill", "biol", "both", "bottom", "brief", "briefly", "but", "by", "c", "ca", "call", "came", "can", "cannot", "cant", "can't", "caption", "cause",
			"causes", "certain", "certainly", "changes", "clearly", "c'mon", "co", "co.", "com", "come", "comes", "computer", "con", "concerning",
			"consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "couldn't", "course", "cry",
			"c's", "currently", "d", "dare", "daren't", "date", "de", "definitely", "describe", "described", "despite", "detail", "did", "didn't", "different",
			"directly", "do", "does", "doesn't", "doing", "done", "don't", "down", "downwards", "due", "during", "e", "each", "ed", "edu", "effect", "eg",
			"eight", "eighty", "either", "eleven", "else", "elsewhere", "empty", "end", "ending", "enough", "entirely", "especially", "et", "et-al", "etc",
			"even", "ever", "evermore", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "f", "fairly",
			"far", "farther", "few", "fewer", "ff", "fifteen", "fifth", "fify", "fill", "find", "fire", "first", "five", "fix", "followed", "following",
			"follows", "for", "forever", "former", "formerly", "forth", "forty", "forward", "found", "four", "from", "front", "full", "further", "furthermore",
			"g", "gave", "get", "gets", "getting", "give", "given", "gives", "giving", "go", "goes", "going", "gone", "got", "gotten", "greetings", "h", "had",
			"hadn't", "half", "happens", "hardly", "has", "hasnt", "hasn't", "have", "haven't", "having", "he", "hed", "he'd", "he'll", "hello", "help",
			"hence", "her", "here", "hereafter", "hereby", "herein", "heres", "here's", "hereupon", "hers", "herse”", "herself", "hes", "he's", "hi", "hid",
			"him", "himse”", "himself", "his", "hither", "home", "hopefully", "how", "howbeit", "however", "how's", "hundred", "i", "I", "id", "i'd", "ie",
			"if", "ignored", "i'll", "im", "i'm", "immediate", "immediately", "importance", "important", "in", "inasmuch", "inc", "inc.", "indeed", "index",
			"indicate", "indicated", "indicates", "information", "inner", "inside", "insofar", "instead", "interest", "into", "invention", "inward", "is",
			"isn't", "it", "itd", "it'd", "it'll", "its", "it's", "itse”", "itself", "i've", "j", "just", "k", "keep", "keeps", "kept", "keys", "kg", "km",
			"know", "known", "knows", "l", "largely", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "let's", "like",
			"liked", "likely", "likewise", "line", "little", "'ll", "look", "looking", "looks", "low", "lower", "ltd", "m", "made", "mainly", "make", "makes",
			"many", "may", "maybe", "mayn't", "me", "mean", "means", "meantime", "meanwhile", "merely", "mg", "might", "mightn't", "mill", "million", "mine",
			"minus", "miss", "ml", "more", "moreover", "most", "mostly", "move", "mr", "mrs", "much", "mug", "must", "mustn't", "my", "myse”", "myself", "n",
			"na", "name", "namely", "nay", "nd", "near", "nearly", "necessarily", "necessary", "need", "needn't", "needs", "neither", "never", "neverf",
			"neverless", "nevertheless", "new", "next", "nine", "ninety", "no", "nobody", "non", "none", "nonetheless", "noone", "no-one", "nor", "normally",
			"nos", "not", "noted", "nothing", "notwithstanding", "novel", "now", "nowhere", "o", "obtain", "obtained", "obviously", "of", "off", "often", "oh",
			"ok", "okay", "old", "omitted", "on", "once", "one", "ones", "one's", "only", "onto", "opposite", "or", "ord", "other", "others", "otherwise",
			"ought", "oughtn't", "our", "ours", "ourselves", "out", "outside", "over", "overall", "owing", "own", "p", "page", "pages", "part", "particular",
			"particularly", "past", "per", "perhaps", "placed", "please", "plus", "poorly", "possible", "possibly", "potentially", "pp", "predominantly",
			"present", "presumably", "previously", "primarily", "probably", "promptly", "proud", "provided", "provides", "put", "q", "que", "quickly", "quite",
			"qv", "r", "ran", "rather", "rd", "re", "readily", "really", "reasonably", "recent", "recently", "ref", "refs", "regarding", "regardless",
			"regards", "related", "relatively", "research", "respectively", "resulted", "resulting", "results", "right", "round", "run", "s", "said", "same",
			"saw", "say", "saying", "says", "sec", "second", "secondly", "section", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self",
			"selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "shan't", "she", "shed", "she'd", "she'll", "shes", "she's",
			"should", "shouldn't", "show", "showed", "shown", "showns", "shows", "side", "significant", "significantly", "similar", "similarly", "since",
			"sincere", "six", "sixty", "slightly", "so", "some", "somebody", "someday", "somehow", "someone", "somethan", "something", "sometime", "sometimes",
			"somewhat", "somewhere", "soon", "sorry", "specifically", "specified", "specify", "specifying", "state", "states", "still", "stop", "strongly",
			"sub", "substantially", "successfully", "such", "sufficiently", "suggest", "sup", "sure", "system", "t", "take", "taken", "taking", "tell", "ten",
			"tends", "th", "than", "thank", "thanks", "thanx", "that", "that'll", "thats", "that's", "that've", "the", "their", "theirs", "them", "themselves",
			"then", "thence", "there", "thereafter", "thereby", "thered", "there'd", "therefore", "therein", "there'll", "thereof", "therere", "there're",
			"theres", "there's", "thereto", "thereupon", "there've", "these", "they", "theyd", "they'd", "they'll", "theyre", "they're", "they've", "thick",
			"thin", "thing", "things", "think", "third", "thirty", "this", "thorough", "thoroughly", "those", "thou", "though", "thoughh", "thousand", "three",
			"throug", "through", "throughout", "thru", "thus", "til", "till", "tip", "to", "together", "too", "took", "top", "toward", "towards", "tried",
			"tries", "truly", "try", "trying", "ts", "t's", "twelve", "twenty", "twice", "two", "u", "un", "under", "underneath", "undoing", "unfortunately",
			"unless", "unlike", "unlikely", "until", "unto", "up", "upon", "ups", "upwards", "us", "use", "used", "useful", "usefully", "usefulness", "uses",
			"using", "usually", "uucp", "v", "value", "various", "'ve", "versus", "very", "via", "viz", "vol", "vols", "vs", "w", "want", "wants", "was",
			"wasn't", "way", "we", "wed", "we'd", "welcome", "well", "we'll", "went", "were", "we're", "weren't", "we've", "what", "whatever", "what'll",
			"whats", "what's", "what've", "when", "whence", "whenever", "when's", "where", "whereafter", "whereas", "whereby", "wherein", "wheres", "where's",
			"whereupon", "wherever", "whether", "which", "whichever", "while", "whilst", "whim", "whither", "who", "whod", "who'd", "whoever", "whole",
			"who'll", "whom", "whomever", "whos", "who's", "whose", "why", "why's", "widely", "will", "willing", "wish", "with", "within", "without", "wonder",
			"won't", "words", "world", "would", "wouldn't", "www", "x", "y", "yes", "yet", "you", "youd", "you'd", "you'll", "your", "youre", "you're", "yours",
			"yourself", "yourselves", "you've", "z", "zero" });

	@Autowired
	private IHelpDAO helpDAO;

	@Autowired
	private IUsersService iUsersService;

	@Autowired
	private IMediaService iMediaService;

	@Autowired
	private IStorageService iStorageService;

	@Override
	public Map<String, Object> save(@Valid final HelpDto helpDto, final String userId) {
		Map<String, Object> response = new HashMap<>();
		try {
			helpDAO.save(convertDtoToSeekaHelp(helpDto, null, userId));
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.HELP_SUCCESS_MESSAGE);
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public SeekaHelp convertDtoToSeekaHelp(final HelpDto dto, final String id, final String userId) {
		SeekaHelp seekaHelp = null;
		if (id != null) {
			seekaHelp = helpDAO.get(id);
		} else {
			seekaHelp = new SeekaHelp();
			seekaHelp.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			seekaHelp.setStatus(HelpEnum.NOTASSIGNED.toString());
		}
		seekaHelp.setUserId(userId);
		seekaHelp.setCategory(helpDAO.getHelpCategory(dto.getCategoryId()));
		seekaHelp.setSubCategory(helpDAO.getHelpSubCategory(dto.getSubCategoryId()));
		seekaHelp.setCreatedBy(dto.getCreatedBy());
		seekaHelp.setUpdatedBy(dto.getUpdatedBy());
		seekaHelp.setTitle(dto.getTitle());
		seekaHelp.setDescritpion(dto.getDescription());
		seekaHelp.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		seekaHelp.setIsActive(true);
		seekaHelp.setIsQuestioning(dto.getIsQuestioning());
		return seekaHelp;
	}

	@Override
	public Map<String, Object> get(final String id) {
		Map<String, Object> response = new HashMap<>();
		HelpDto dto = null;
		try {
			dto = convertSeekaHelpToDto(helpDAO.get(id));
			if (dto != null) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.HELP_SUCCESS);
				response.put("data", dto);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public HelpDto convertSeekaHelpToDto(final SeekaHelp seekaHelp) {
		HelpDto dto = new HelpDto();

		dto.setId(seekaHelp.getId());
		dto.setTitle(seekaHelp.getTitle());
		dto.setDescription(seekaHelp.getDescritpion());
		dto.setCategoryId(seekaHelp.getCategory().getId());
		dto.setSubCategoryId(seekaHelp.getSubCategory().getId());
		dto.setCreatedBy(seekaHelp.getCreatedBy());
		dto.setUpdatedBy(seekaHelp.getUpdatedBy());
		dto.setIsQuestioning(seekaHelp.getIsQuestioning());
		dto.setStatus(seekaHelp.getStatus());
		dto.setIsArchive(seekaHelp.getIsArchive());
		if (seekaHelp.getUserId() != null) {
			try {
				UserDto userDto = iUsersService.getUserById(seekaHelp.getUserId());
				if (userDto != null) {
					if (userDto.getFirstName() != null) {
						dto.setCreatedUser(userDto.getFirstName());
					}
					if ((userDto.getFirstName() != null) && (userDto.getLastName() != null)) {
						dto.setCreatedUser(userDto.getFirstName() + " " + userDto.getLastName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (seekaHelp.getCreatedOn() != null) {
			dto.setCreatedOn(DateUtil.convertDateToString(seekaHelp.getCreatedOn()));
		}
		return dto;
	}

	@Override
	public Map<String, Object> update(final HelpDto helpDto, final String id, final String userId) {
		Map<String, Object> response = new HashMap<>();
		try {
			helpDAO.update(convertDtoToSeekaHelp(helpDto, id, userId));
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.HELP_UPDATE_MESSAGE);
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public Map<String, Object> getAll(final Integer pageNumber, final Integer pageSize) {
		Map<String, Object> response = new HashMap<>();
		String status = IConstant.SUCCESS;
		List<SeekaHelp> helps = new ArrayList<>();
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		try {
			totalCount = helpDAO.findTotalHelpRecord(null, null);
			int startIndex = (pageNumber - 1) * pageSize;
			paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
			helps = helpDAO.getAll(startIndex, pageSize, null, null);
		} catch (Exception exception) {
			status = IConstant.FAIL;
		}
		response.put("status", HttpStatus.OK.value());
		response.put("message", status);
		response.put("courses", helps);
		response.put("totalCount", totalCount);
		response.put("pageNumber", paginationUtilDto.getPageNumber());
		response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		response.put("hasNextPage", paginationUtilDto.isHasNextPage());
		response.put("totalPages", paginationUtilDto.getTotalPages());
		return response;
	}

	@Override
	public Map<String, Object> save(@Valid final HelpCategoryDto helpCategoryDto) {
		Map<String, Object> response = new HashMap<>();
		try {
			helpDAO.save(convertHelpCategoryDtoToBean(helpCategoryDto));
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.HELP_CATEGORY_SUCCESS_MESSAGE);
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public HelpCategory convertHelpCategoryDtoToBean(final HelpCategoryDto helpCategoryDto) {
		HelpCategory helpCategory = new HelpCategory();
		helpCategory.setName(helpCategoryDto.getName());
		helpCategory.setCreatedBy(helpCategoryDto.getCreatedBy());
		helpCategory.setUpdatedBy(helpCategoryDto.getUpdatedBy());
		helpCategory.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		helpCategory.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		helpCategory.setIsActive(true);
		return helpCategory;

	}

	@Override
	public Map<String, Object> save(@Valid final HelpSubCategoryDto helpSubCategoryDto) {
		Map<String, Object> response = new HashMap<>();
		try {
			helpDAO.save(convertHelpCategoryDtoToBean(helpSubCategoryDto));
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.HELP_SUBCATEGORY_SUCCESS_MESSAGE);
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public HelpSubCategory convertHelpCategoryDtoToBean(final HelpSubCategoryDto helpSubCategoryDto) {
		HelpSubCategory helpSubCategory = new HelpSubCategory();
		helpSubCategory.setName(helpSubCategoryDto.getName());
		helpSubCategory.setCreatedBy(helpSubCategoryDto.getCreatedBy());
		helpSubCategory.setUpdatedBy(helpSubCategoryDto.getUpdatedBy());
		helpSubCategory.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		helpSubCategory.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		helpSubCategory.setCategoryId(helpDAO.getHelpCategory(helpSubCategoryDto.getCategoryId()));
		helpSubCategory.setIsActive(true);
		return helpSubCategory;
	}

	@Override
	public Map<String, Object> getCategory(final String id) {
		Map<String, Object> response = new HashMap<>();
		HelpCategoryDto dto = null;
		try {
			dto = convertHelpCategoryToDto(helpDAO.getHelpCategory(id));
			if (dto != null) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.HELP_CATEGORY_SUCCESS);
				response.put("data", dto);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_CATEGORY_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public HelpCategoryDto convertHelpCategoryToDto(final HelpCategory helpCategory) {
		HelpCategoryDto helpCategoryDto = new HelpCategoryDto();
		helpCategoryDto.setCreatedBy(helpCategory.getCreatedBy());
		helpCategoryDto.setName(helpCategory.getName());
		helpCategoryDto.setUpdatedBy(helpCategory.getUpdatedBy());
		helpCategoryDto.setId(helpCategory.getId());
		return helpCategoryDto;
	}

	@Override
	public Map<String, Object> getSubCategory(final String id) {
		Map<String, Object> response = new HashMap<>();
		HelpSubCategoryDto dto = null;
		try {
			dto = convertHelpSubCategoryToDto(helpDAO.getHelpSubCategory(id));
			if (dto != null) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.HELP_SUBCATEGORY_SUCCESS);
				response.put("data", dto);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_SUBCATEGORY_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public HelpSubCategoryDto convertHelpSubCategoryToDto(final HelpSubCategory helpSubCategory) {
		HelpSubCategoryDto helpSubCategoryDto = new HelpSubCategoryDto();
		helpSubCategoryDto.setCategoryId(helpSubCategory.getCategoryId().getId());
		helpSubCategoryDto.setName(helpSubCategory.getName());
		helpSubCategoryDto.setCreatedBy(helpSubCategory.getCreatedBy());
		helpSubCategoryDto.setUpdatedBy(helpSubCategory.getUpdatedBy());
		helpSubCategoryDto.setId(helpSubCategory.getId());
		helpSubCategoryDto.setHelpCount(helpSubCategory.getHelpCount());
		return helpSubCategoryDto;
	}

	@Override
	public List<HelpSubCategoryDto> getSubCategoryByCategory(final String categoryId, final Integer startIndex, final Integer pageSize) {
		List<HelpSubCategoryDto> subCategoryDtos = new ArrayList<>();
		List<HelpSubCategory> categories = helpDAO.getSubCategoryByCategory(categoryId, startIndex, pageSize);
		for (HelpSubCategory helpSubCategory : categories) {
			helpSubCategory.setHelpCount(helpDAO.findTotalHelpRecordBySubCategory(helpSubCategory.getId()));
			subCategoryDtos.add(convertHelpSubCategoryToDto(helpSubCategory));
		}
		return subCategoryDtos;
	}

	@Override
	public Map<String, Object> getHelpByCategory(final String categoryId) {
		Map<String, Object> response = new HashMap<>();
		List<HelpDto> helpDtos = new ArrayList<>();
		try {
			List<SeekaHelp> seekHelps = helpDAO.getHelpByCategory(categoryId);
			try {
				for (SeekaHelp seekaHelp : seekHelps) {
					helpDtos.add(convertSeekaHelpToDto(seekaHelp));
				}
			} catch (Exception exception) {
			}
			if ((helpDtos != null) && !helpDtos.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.HELP_SUCCESS);
				response.put("data", helpDtos);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public Map<String, Object> getSubCategoryCount() {
		Map<String, Object> response = new HashMap<>();
		List<HelpSubCategory> helpSubCategories = new ArrayList<>();
		try {
			List<HelpSubCategory> subCatgories = helpDAO.getAllHelpSubCategories();
			for (HelpSubCategory helpSubCategory : subCatgories) {
				helpSubCategory.setHelpCount(helpDAO.findTotalHelpRecordBySubCategory(helpSubCategory.getId()));
				helpSubCategories.add(helpSubCategory);
			}
			if ((helpSubCategories != null) && !helpSubCategories.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.HELP_SUBCATEGORY_SUCCESS);
				response.put("data", helpSubCategories);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_SUBCATEGORY_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public Map<String, Object> saveAnswer(@Valid final HelpAnswerDto helpAnswerDto, final MultipartFile file) {
		Map<String, Object> response = new HashMap<>();
		try {
			HelpAnswer helpAnswer = helpDAO.save(convertDtoToHelpAnswerBeans(helpAnswerDto));
			if ((helpAnswer != null) && (file != null)) {
				String logoName = iMediaService.uploadImage(file, helpAnswer.getId(), ImageCategory.HELP_SUPPORT.name(), null);
				System.out.println("Help answer media upload for id - >" + helpAnswer.getId() + " and Image  name :" + logoName);
				if ((logoName != null) && !logoName.isEmpty() && !logoName.equals("null")) {
					helpAnswer.setFileName(logoName);
					helpDAO.updateAnwser(helpAnswer);
				}
			}
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.HELP_ANSWER_SUCCESS_MESSAGE);
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public HelpAnswer convertDtoToHelpAnswerBeans(final HelpAnswerDto answerDto) {
		HelpAnswer helpAnswer = new HelpAnswer();
		helpAnswer.setAnswer(answerDto.getAnswer());
		helpAnswer.setSeekaHelp(helpDAO.get(answerDto.getHelpId()));
		helpAnswer.setUser(answerDto.getUserId());
		helpAnswer.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		helpAnswer.setCreatedBy(answerDto.getCreatedBy());
		helpAnswer.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		helpAnswer.setUpdatedBy(answerDto.getUpdatedBy());
		helpAnswer.setIsDeleted(false);
		return helpAnswer;
	}

	@Override
	public Map<String, Object> getAnswerByHelpId(final String helpId) {
		Map<String, Object> response = new HashMap<>();
		List<HelpAnswerDto> dtos = new ArrayList<>();
		try {
			List<HelpAnswer> helpAnswers = helpDAO.getAnswerByHelpId(helpId);
			for (HelpAnswer helpAnswer : helpAnswers) {
				dtos.add(convertBeanToHelpAnswerDto(helpAnswer));
			}
			if ((dtos != null) && !dtos.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.HELP_ANSWER_SUCCESS);
				response.put("data", dtos);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_ANSWER_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	public HelpAnswerDto convertBeanToHelpAnswerDto(final HelpAnswer helpAnswer) {
		HelpAnswerDto helpAnswerDto = new HelpAnswerDto();
		helpAnswerDto.setAnswer(helpAnswer.getAnswer());
		helpAnswerDto.setUserId(helpAnswer.getUser());
		helpAnswerDto.setHelpId(helpAnswer.getSeekaHelp().getId());
		helpAnswerDto.setCreatedBy(helpAnswer.getCreatedBy());
		helpAnswerDto.setUpdatedBy(helpAnswer.getUpdatedBy());
		if (helpAnswer.getCreatedOn() != null) {
			helpAnswerDto.setCreatedOn(DateUtil.convertDateToString(helpAnswer.getCreatedOn()));
		}
		if (helpAnswer.getFileName() != null) {
			try {
				List<StorageDto> storageDTOList = iStorageService.getStorageInformation(helpAnswer.getId(), ImageCategory.HELP_SUPPORT.toString(), null, "en");
				if ((storageDTOList != null) && !storageDTOList.isEmpty()) {
					StorageDto storageDto = storageDTOList.get(0);
					if (storageDto != null) {
						helpAnswerDto.setFileUrl(storageDto.getImageURL());
					}
				}
			} catch (ValidationException e) {
				e.printStackTrace();
			}
		}
		return helpAnswerDto;
	}

	@Override
	public List<HelpCategoryDto> getCategory(final Integer startIndex, final Integer pageSize) {
		List<HelpCategoryDto> dtos = new ArrayList<>();
		List<HelpCategory> categories = helpDAO.getAllCategory(startIndex, pageSize);
		for (HelpCategory category : categories) {
			dtos.add(convertHelpCategoryToDto(category));
		}
		return dtos;
	}

	@Override
	public Map<String, Object> delete(@Valid final String id) {
		Map<String, Object> response = new HashMap<>();
		try {
			SeekaHelp help = helpDAO.get(id);
			if (help != null) {
				help.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
				help.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
				help.setIsActive(false);
				helpDAO.update(help);
				response.put("status", HttpStatus.OK.value());
				response.put("message", "Help deleted successfully");
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public Map<String, Object> updateStatus(final String id, final String userId, final String status) {
		Map<String, Object> response = new HashMap<>();
		try {
			SeekaHelp help = helpDAO.get(id);
			if (help != null) {
				help.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
				help.setStatus(status);
				if (userId != null) {
					help.setAssignedUserId(userId);
				}
				helpDAO.update(help);
				response.put("status", HttpStatus.OK.value());
				response.put("message", "Help deleted successfully");
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public Map<String, Object> filter(final String status, final String mostRecent, final String categoryId) {
		Map<String, Object> response = new HashMap<>();
		List<HelpDto> helpDtos = new ArrayList<>();
		try {
			List<SeekaHelp> seekaHelps = new ArrayList<>();
			if ((status != null) && !status.isEmpty()) {
				seekaHelps = helpDAO.findByStatus(status, categoryId);
			}
			if ((mostRecent != null) && !mostRecent.isEmpty()) {
				seekaHelps = helpDAO.findByMostRecent(mostRecent, categoryId);
			}
			if ((status != null) && !status.isEmpty() && (mostRecent != null) && !mostRecent.isEmpty()) {
				seekaHelps = helpDAO.findByStatusAndMostRecent(status, mostRecent, categoryId);
			}
			if ((status == null) && (mostRecent == null)) {
				seekaHelps = helpDAO.getHelpByCategory(categoryId);
			}
			for (SeekaHelp seekaHelp : seekaHelps) {
				helpDtos.add(convertSeekaHelpToDto(seekaHelp));
			}
			if ((helpDtos != null) && !helpDtos.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.HELP_SUCCESS);
				response.put("data", helpDtos);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.HELP_NOT_FOUND);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public List<SeekaHelp> getUserHelpList(final String userId, final int startIndex, final Integer pageSize, final Boolean isArchive) {
		return helpDAO.getAll(startIndex, pageSize, userId, isArchive);
	}

	@Override
	public int getUserHelpCount(final String userId, final Boolean isArchive) {
		return helpDAO.findTotalHelpRecord(userId, isArchive);
	}

	@Override
	public void setIsFavouriteFlag(final String id, final boolean isFavourite) throws NotFoundException {
		helpDAO.setIsFavouriteFlag(id, isFavourite);
	}

	@Override
	public int getCategoryCount() {
		return helpDAO.getCategoryCount();
	}

	@Override
	public int getSubCategoryCount(final String categoryId) {
		return helpDAO.getSubCategoryCount(categoryId);
	}

	@Override
	public void archiveHelpSupport(final String entityId, final boolean isArchive) {
		SeekaHelp seekaHelp = helpDAO.get(entityId);
		seekaHelp.setIsArchive(isArchive);
		seekaHelp.setUpdatedBy("API");
		seekaHelp.setUpdatedOn(new Date());
		helpDAO.update(seekaHelp);
	}

	@Override
	public List<String> getRelatedSearchQuestions(String searchString) throws ValidationException {
		searchString = searchString.toLowerCase();
		List<String> searchKeywords = new LinkedList<>(Arrays.asList(searchString.split(" ")));
		searchKeywords.removeAll(stopWordsForRelatedQuestions);
		if ((searchKeywords == null) || searchKeywords.isEmpty()) {
			throw new ValidationException("Please Search using proper keywords");
		}
		System.out.println(searchKeywords);

		return helpDAO.getRelatedSearchQuestions(searchKeywords);
	}

}
