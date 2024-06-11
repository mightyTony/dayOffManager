package mightytony.sideproject.dayoffmanager.company.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CompanySearchResponseDto {
    @JsonProperty("request_cnt")
    private int requestCnt;

    @JsonProperty("valid_cnt")
    private int validCnt;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("data")
    private List<BusinessData> data;

    @Data
    static class BusinessData {
        @JsonProperty("b_no")
        private String bNo;

        @JsonProperty("valid")
        private String valid;

        @JsonProperty("valid_msg")
        private String validMsg;

        @JsonProperty("request_param")
        private RequestParam requestParam;

        @JsonProperty("status")
        private BusinessStatus status;
    }

    @Data
    static class RequestParam {
        @JsonProperty("b_no")
        private String bNo;

        @JsonProperty("start_dt")
        private String startDt;

        @JsonProperty("p_nm")
        private String pNm;

        @JsonProperty("b_nm")
        private String bNm;
    }

    @Data
    static class BusinessStatus {
        @JsonProperty("b_no")
        private String bNo;

        @JsonProperty("b_stt")
        private String bStt;

        @JsonProperty("b_stt_cd")
        private String bSttCd;

        @JsonProperty("tax_type")
        private String taxType;

        @JsonProperty("tax_type_cd")
        private String taxTypeCd;

        @JsonProperty("end_dt")
        private String endDt;

        @JsonProperty("utcc_yn")
        private String utccYn;

        @JsonProperty("tax_type_change_dt")
        private String taxTypeChangeDt;

        @JsonProperty("invoice_apply_dt")
        private String invoiceApplyDt;

        @JsonProperty("rbf_tax_type")
        private String rbfTaxType;

        @JsonProperty("rbf_tax_type_cd")
        private String rbfTaxTypeCd;
    }
}