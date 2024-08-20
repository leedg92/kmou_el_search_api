package com.saltlux.searchstudio.api;

public class ApiConstants {

    /**
     * 시간 체크를 위한 로그 포맷.<br/> 형식: [TimeCheck][처리명][시간(ms)]
     */
    public static final String LOG_FORMAT_TIME_CHECK = "[TimeCheck][{}][{}]";
    public static final String HIGHLIGHT_HEAD = "<b>";
    public static final String HIGHLIGHT_TAIL = "</b>";

    /**
     * 색인
     */
    public static class Index {

        /**
         * 개설과목
         */
        public static final String LECTURE = "lecture";
        /**
         * 개설과목 테스트
         */
//        public static final String TEST_LECTURE = "test_lecture";
        /**
         * 교육과정
         */
        public static final String CURRICULUM = "curriculum";
        /**
         * 비교과
         */
        public static final String NON_LECTURE = "non_lecture";
        /**
         * 교수
         */
        public static final String PROFESSOR = "professor";
        /**
         * 전공
         */
        public static final String MAJOR = "major";
    }

    /**
     * 색인 필드
     */
    public static class Field {

        /**
         * 통합검색 필드
         */
        public static final String _SEARCH = "_search";
        
        /**
         * 교과목 필드
         */
        public static final String _SBJT_BIGRAM = "subject_bigram";
        public static final String _SBJT_MORPH= "subject_morph";
        
        /** 
         * 교수 필드
         */
        public static final String _EMP_BIGRAM = "emp_bigram";
        public static final String _EMP_MORPH= "emp_morph";
        
        /**
         * ID
         */
        public static final String ID = "id";

        /**
         * 교육과정 ID
         */
        public static final String LECTURE_ID = "LECTURE_ID";
        
        /**
         * 중복 ID 
         */
        public static final String COLLAPSE_ID = "COLLAPSE_ID";
        
        /**
         * 연도
         */
        public static final String YEAR = "YEAR";
        /**
         * 학기
         */
        public static final String SMT_CD = "SMT_CD";
        /**
         * 학기명
         */
        public static final String SMT_NM = "SMT_NM";
        /**
         * 교과목코드
         */
        public static final String SUBJECT_CD = "SUBJECT_CD";
        /**
         * 교과목명
         */
        public static final String SUBJECT_NM = "SUBJECT_NM";
        /**
         * 교과목명(영문)
         */
        public static final String SUBJECT_ENM = "SUBJECT_ENM";
        /**
         * 개설학과코드
         */
        public static final String DEPT_CD = "DEPT_CD";
        /**
         * 개설학과명
         */
        public static final String DEPT_NM = "DEPT_NM";
        /**
         * 개설대학코드
         */
        public static final String COLG_CD = "COLG_CD";
        /**
         * 개설대학명
         */
        public static final String COLG_NM = "COLG_NM";
        /**
         * 전공코드
         */
        public static final String MAJOR_CD = "MAJOR_CD";
        /**
         * 전공명
         */
        public static final String MAJOR_NM = "MAJOR_NM";
        /**
         * 전공 : 전공명(국문)
         */
        public static final String MAJOR_NM_KOR = "MAJOR_NM_KOR";
        /**
         * 전공 : 전공명(영문)
         */
        public static final String MAJOR_NM_ENG = "MAJOR_NM_ENG";
        /**
         * 개설학년
         */
        public static final String GRADE = "GRADE";
        /**
         * 신분구분코드
         */
        public static final String SINBUN_CD = "SINBUN_CD";
        /**
         * 신분구분코드명
         */
        public static final String SINBUN_NM = "SINBUN_NM";
        /**
         * 교과구분코드
         */
        public static final String COMDIV_CD = "COMDIV_CD";
        /**
         * 교과구분코드명
         */
        public static final String COMDIV_NM = "COMDIV_NM";
        /**
         * 학위과정구분코드
         */
        public static final String DEG_GB_CD = "DEG_GB_CD";
        /**
         * 학위과정구분코드명
         */
        public static final String DEG_GB_NM = "DEG_GB_NM";
        /**
         * 부전공교과구분코드
         */
        public static final String MNRCOM_DIV_CD = "MNRCOM_DIV_CD";
        /**
         * 부전공교과구분명
         */
        public static final String MNRCOM_DIV_NM = "MNRCOM_DIV_NM";
        /**
         * 분반
         */
        public static final String DIVCLS = "DIVCLS";
        /**
         * 학점
         */
        public static final String CDT_NUM = "CDT_NUM";
        /**
         * 이론시간
         */
        public static final String WTIME_NUM = "WTIME_NUM";
        /**
         * 실습시간
         */
        public static final String PTIME_NUM = "PTIME_NUM";
        /**
         * 시수
         */
        public static final String SISU = "SISU";
        /**
         * 이수과목수
         */
        public static final String COM_CNT = "COM_CNT";
        /**
         * 다전공(융합/연계)전공 개설학부(과)코드
         */
        public static final String FUSE_DEPT_CD = "FUSE_DEPT_CD";
        /**
         * 다전공(융합/연계)전공 개설학부(과)명
         */
        public static final String FUSE_DEPT_NM = "FUSE_DEPT_NM";
        /**
         * 다전공(융합/연계)전공 영역코드
         */
        public static final String FUSE_DOMAIN_CD = "FUSE_DOMAIN_CD";
        /**
         * 다전공(융합/연계)전공 영역명
         */
        public static final String FUSE_DOMAIN_NM = "FUSE_DOMAIN_NM";
        /**
         * 다전공구분코드
         */
        public static final String MAJOR_GB_CD = "MAJOR_GB_CD";
        /**
         * 다전공구분명
         */
        public static final String MAJOR_GB_NM = "MAJOR_GB_NM";
        /**
         * 교과목개요(국문)
         */
        public static final String SUBJ_DESC_KOR = "SUBJ_DESC_KOR";
        /**
         * 교과목개요(영문)
         */
        public static final String SUBJ_DESC_ENG = "SUBJ_DESC_ENG";
        /**
         * 교수번호
         */
        public static final String EMP_NO = "EMP_NO";
        /**
         * 교수명
         */
        public static final String EMP_NM = "EMP_NM";
        /**
         * 교수명(영문)
         */
        public static final String EMP_ENM = "EMP_ENM";
        /**
         * 교수명(한문)
         */
        public static final String EMP_CNM = "EMP_CNM";
        /**
         * 강좌구분코드
         */
        public static final String CLASS_CD = "CLASS_CD";
        /**
         * 강좌구분명
         */
        public static final String CLASS_NM = "CLASS_NM";
        /**
         * 강좌유형코드
         */
        public static final String PYENGGA_TP_CD = "PYENGGA_TP_CD";
        /**
         * 강좌유형명
         */
        public static final String PYENGGA_TP_NM = "PYENGGA_TP_NM";
        /**
         * 폐강유무
         */
        public static final String ABO_YN = "ABO_YN";
        /**
         * 교과목개요
         */
        public static final String SUBJ_DESC = "SUBJ_DESC";
        /**
         * 강의운영방법
         */
        public static final String METHOD = "METHOD";
        /**
         * 핵십역량 연계 목표
         */
        public static final String CORE_GOAL = "CORE_GOAL";
        /**
         * 전공능력 연계 목표
         */
        public static final String MAJOR_GOAL = "MAJOR_GOAL";
        /**
         * 수업목표
         */
        public static final String CLASS_GOAL = "CLASS_GOAL";
        /**
         * CQI 개선된점
         */
        public static final String CQI_IMP = "CQI_IMP";
        /**
         * 핵심역량 주역량
         */
        public static final String MAIN_ABI_NM = "MAIN_ABI_NM";
        /**
         * 핵심역량 부역량
         */
        public static final String SUB_ABI_NM = "SUB_ABI_NM";
        /**
         * 강의계획서 1~15주
         */
        public static final String CURI_CONTENT = "CURI_CONTENT";
        /**
         * 프로그램 고유값
         */
        public static final String IDX = "IDX";
        /**
         * 프로그램 주제번호
         */
        public static final String TIDX = "TIDX";
        /**
         * 프로그램 그룹고유값
         */
        public static final String GIDX = "GIDX";
        /**
         * 프로그램명
         */
        public static final String TITLE = "TITLE";
        /**
         * 프로그램 주제명
         */
        public static final String TOPIC = "TOPIC";
        /**
         * 학기
         */
        public static final String SEMESTER = "SEMESTER";
        /**
         * 마일리지
         */
        public static final String POINT = "POINT";
        /**
         * 총시간
         */
        public static final String TIME = "TIME";
        /**
         * 횟수
         */
        public static final String COUNT = "COUNT";
        /**
         * 1차카테고리
         */
        public static final String MAIN_CATEGORY_CD = "MAIN_CATEGORY_CD";
        /**
         * 1차카테고리명
         */
        public static final String MAIN_CATEGORY_NM = "MAIN_CATEGORY_NM";
        /**
         * 2차카테고리
         */
        public static final String SUB_CATEGORY_CD = "SUB_CATEGORY_CD";
        /**
         * 2차카테고리명
         */
        public static final String SUB_CATEGORY_NM = "SUB_CATEGORY_NM";
        /**
         * 핵심역량 주역량
         */
        public static final String MAIN_ESSENTIAL = "MAIN_ESSENTIAL";
        /**
         * 핵심역량 부역량
         */
        public static final String SUB_ESSENTIAL = "SUB_ESSENTIAL";
        /**
         * 프로그램 형식
         */
        public static final String TYPE_CD = "TYPE_CD";
        /**
         * 프로그램 형식명
         */
        public static final String TYPE_NM = "TYPE_NM";
        /**
         * 운영방식
         */
        public static final String METHOD_CD = "METHOD_CD";
        /**
         * 운영방식명
         */
        public static final String METHOD_NM = "METHOD_NM";
        /**
         * 프로그램 관련 태그
         */
        public static final String PROGRAM_TAG = "PROGRAM_TAG";
        /**
         * 교육목표
         */
        public static final String EDUCATION_GOAL = "EDUCATION_GOAL";
        /**
         * 담당자정보
         */
        public static final String DEPARTMENT = "DEPARTMENT";
        /**
         * 이메일주소
         */
        public static final String EMAIL = "EMAIL";
        /**
         * 담당자연락처
         */
        public static final String CONTACT = "CONTACT";
        /**
         * 장소
         */
        public static final String LOCATION = "LOCATION";
        /**
         * 신청방법
         */
        public static final String APPLICATION_METHOD = "APPLICATION_METHOD";
        /**
         * 프로그램개요
         */
        public static final String ABSTRACT = "ABSTRACT";
        /**
         * 표지이미지고유값
         */
        public static final String COVER = "COVER";
        /**
         * 프로그램 시작일시
         */
        public static final String START_DATE = "START_DATE";
        /**
         * 프로그램 종료일시
         */
        public static final String END_DATE = "END_DATE";
        /**
         * 프로그램 신청시작일시
         */
        public static final String SIGNIN_START_DATE = "SIGNIN_START_DATE";
        /**
         * 프로그램 신청종료일시
         */
        public static final String SIGNIN_END_DATE = "SIGNIN_END_DATE";
        /**
         * 신청형태
         */
        public static final String SIGNIN_TYPE = "SIGNIN_TYPE";
        /**
         * 신청대상
         */
        public static final String SIGNIN_TARGET = "SIGNIN_TARGET";
        /**
         * 신청대상재적상태
         */
        public static final String SIGNIN_STATUS = "SIGNIN_STATUS";
        /**
         * 신청대상학년
         */
        public static final String SIGNIN_GRADE = "SIGNIN_GRADE";
        /**
         * 신청대상성별
         */
        public static final String SIGNIN_GENDER = "SIGNIN_GENDER";
        /**
         * 신청대상단과대학
         */
        public static final String SIGNIN_IIDX = "SIGNIN_IIDX";
        /**
         * 신청대상학과
         */
        public static final String SIGNIN_DIDX = "SIGNIN_DIDX";
        /**
         * 신청대상전공
         */
        public static final String SIGNIN_JIDX = "SIGNIN_JIDX";
        /**
         * 프로그램상세내용
         */
        public static final String CONTENT_TEXT = "CONTENT_TEXT";
        /**
         * 개설근거 상세
         */
        public static final String BASIS_DETAIL = "BASIS_DETAIL";
        /**
         * 개설근거 목적
         */
        public static final String BASIS_PURPOSE = "BASIS_PURPOSE";
        /**
         * 핵심역량 전용
         */
        public static final String ESSENTIAL_ROLE = "ESSENTIAL_ROLE";
        /**
         * 프로그램승인상태
         */
        public static final String CONFIRM_STATUS = "CONFIRM_STATUS";
        /**
         * 마감승인상태
         */
        public static final String COMPLETE_STATUS = "COMPLETE_STATUS";
        /**
         * 프로그램등급
         */
        public static final String COMPLETE_GRADE = "COMPLETE_GRADE";
        /**
         * 홈페이지게시여부
         */
        public static final String IS_VISIBLE = "IS_VISIBLE";
        /**
         * 이수처리여부
         */
        public static final String IS_COMPLETE = "IS_COMPLETE";
        /**
         * 삭제여부
         */
        public static final String IS_DELETE = "IS_DELETE";
        /**
         * 현직급코드
         */
        public static final String JOBG_CD = "JOBG_CD";
        /**
         * 현직급코드명
         */
        public static final String JOBG_NM = "JOBG_NM";
        /**
         * 전공
         */
        public static final String MAJOR = "MAJOR";
        /**
         * 실험실
         */
        public static final String LABRUM = "LABRUM";
        /**
         * 실험실소개
         */
        public static final String LABRUM_INTRO = "LABRUM_INTRO";
        /**
         * 전화
         */
        public static final String TLPHON = "TLPHON";
        /**
         * 주요약력
         */
        public static final String MAIN = "MAIN";
        /**
         * 주요경력
         */
        public static final String CAREER = "CAREER";
        /**
         * 주요연구분야
         */
        public static final String RSRCH_REALM = "RSRCH_REALM";
        /**
         * 첨부파일 경로
         */
        public static final String FILE_PATH = "FILE_PATH";
        /**
         * 퇴직여부
         */
        public static final String RTI_FL = "RTI_FL";
        /**
         * 전공 : 진출분야/전문가
         */
        public static final String FIELD = "FIELD";
        /**
         * 전공 : 인재상
         */
        public static final String TALENT = "TALENT";
        /**
         * 전공 : 자격증
         */
        public static final String LICENSE_NM = "LICENSE_NM";
        /**
         * 전공 : 전공 소개
         */
        public static final String MAJOR_INTRO = "MAJOR_INTRO";
        /**
         * 전공 : 전공 목표(학부/과)
         */
        public static final String GOAL = "GOAL";
        /**
         * 전공 : 졸업 후 진로
         */
        public static final String GRDT_AF_CARR = "GRDT_AF_CARR";
        /**
         * 전공 : 전공능력
         */
        public static final String MAJOR_ABTY = "MAJOR_ABTY";
        /**
         * 전공 : 비교과 과목명
         */
        public static final String NON_SBJT_NM = "NON_SBJT_NM";

        /**
         * 비교과 신청시작요일
         */
        public static final String SIGNIN_START_DAY = "SIGNIN_START_DAY";
        /**
         * 비교과 신청종료요일
         */
        public static final String SIGNIN_END_DAY = "SIGNIN_END_DAY";
        /**
         * 비교과 교육시작요일
         */
        public static final String START_DAY = "START_DAY";
        /**
         * 비교과 교육종료요일
         */
        public static final String END_DAY = "END_DAY";
        /**
         * 비교과 참가자수
         */
        public static final String PARTICIPANT = "PARTICIPANT";
        /**
         * 비교과 참가제한수
         */
        public static final String SIGNIN_LIMIT = "SIGNIN_LIMIT";
        /**
         * 비교과 디데이
         */
        public static final String D_DAY = "D_DAY";

        /**
         * 비교과 정렬
         */
        public static final String REG_DATE_RANK = "REG_DATE_RANK";
        public static final String PARTICIPANT_RATE_RANK = "PARTICIPANT_RATE_RANK";
        public static final String SIGNIN_END_RANK = "SIGNIN_END_RANK";

        /**
         * 교과목핵심역량(테스트)
         */
//        public static final String ESSENTIAL_ABI = "ESSENTIAL_ABI";
        /**
         * 교과목전공능력(테스트)
         */
//        public static final String MAJOR_ABI = "MAJOR_ABI";


    }

    /**
     * 검색어
     */
    public static final String PARAMETER_KEYWORD = "keyword";
    /**
     * 시작날짜
     */
    public static final String PARAMETER_START_DATE = "start_date";
    /**
     * 종료날짜
     */
    public static final String PARAMETER_END_DATE = "end_date";
    /**
     * 폐강유무
     */
    public static final String PARAMETER_ABO_YN = "abo_yn";
    /**
     * 대학코드
     */
    public static final String PARAMETER_UNIVERSITY = "university";
    /**
     * 학과코드
     */
    public static final String PARAMETER_DEPARTMENT = "department";
    /**
     * 전공코드
     */
    public static final String PARAMETER_MAJOR = "major";
    /**
     * 년도
     */
    public static final String PARAMETER_YEAR = "year";
    /**
     * 학기
     */
    public static final String PARAMETER_SEMESTER = "semester";
    /**
     * 학년
     */
    public static final String PARAMETER_GRADE = "grade";
    /**
     * 이수구분 (전공필수, 전공선택...)
     */
    public static final String PARAMETER_COURSE_CLASSIFICATION = "course_classification";
    /**
     * 핵심역량
     */
    public static final String PARAMETER_CORE_COMPETENCE = "core_competence";
    /**
     * 전공구분
     */
    public static final String PARAMETER_MAJOR_CLASSIFICATION = "major_classification";

}
