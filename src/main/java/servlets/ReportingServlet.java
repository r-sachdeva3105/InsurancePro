package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;
import service.PolicyService;
import service.ClaimService;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

public class ReportingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService;
    private PolicyService policyService;
    private ClaimService claimService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService(getServletContext());
        policyService = new PolicyService(getServletContext());
        claimService = new ClaimService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String reportType = request.getParameter("reportType");
        PrintWriter out = response.getWriter();

        if (reportType != null) {
            System.out.println(reportType);
            switch (reportType) {
                case "allCustomers":
                    String customerJson = convertToJson(customerService.getCustomerCountByBrokerID());
                    response.getWriter().write(customerJson);
                    break;

                case "totalPolicies":
                    String policyJson = convertToJsonPolicy(policyService.getTotalPolicies());
                    response.getWriter().write(policyJson);
                    break;

                case "claimsSummary":
                    String claimsJson = convertToJsonClaim(claimService.getTotalClaims());
                    response.getWriter().write(claimsJson);
                    break;


                case "claimsRate":
                    String claimsRateJson = convertToJsonClaimsRate(claimService.getClaimsRate());
                    response.getWriter().write(claimsRateJson);
                    break;

                default:
                    // Return a JSON error message instead of HTML
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 error
                    out.write("{\"error\":\"Invalid Report Type\"}");
                    break;
            }
        } else {
            // Return a JSON error message for no report type
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 error
            out.write("{\"error\":\"No Report Type Selected\"}");
        }
        out.close();
    }

    public String convertToJson(List<Object[]> allCustomers) {
        StringBuilder json = new StringBuilder("[");
        StringJoiner customerJoiner = new StringJoiner(",");

        for (Object[] row : allCustomers) {
            int broker_id = (int) row[0];
            String broker_name = (String) row[1];
            Long customer_count = (Long) row[2];

            StringBuilder customerJson = new StringBuilder();
            customerJson.append("{")
                    .append("\"broker_id\":").append(broker_id).append(",")
                    .append("\"broker_name\":\"").append(broker_name).append("\",")
                    .append("\"customer_count\":\"").append(customer_count).append("\"")
                    .append("}");

            customerJoiner.add(customerJson.toString());
        }

        json.append(customerJoiner.toString()).append("]");
        return json.toString();
    }

    public String convertToJsonPolicy(List<Long> totalPolicies) {
        StringBuilder json = new StringBuilder("[");
        StringJoiner policyJoiner = new StringJoiner(",");

        for (Long total_policies : totalPolicies) {
            StringBuilder policyJson = new StringBuilder();
            policyJson.append("{")
                    .append("\"total_policies\":").append(total_policies)
                    .append("}");

            policyJoiner.add(policyJson.toString());
        }

        json.append(policyJoiner.toString()).append("]");
        return json.toString();
    }

    public String convertToJsonClaim(List<Object[]> totalClaims) {
        StringBuilder json = new StringBuilder("[");
        StringJoiner claimJoiner = new StringJoiner(",");

        for (Object[] row : totalClaims) {
            int broker_id = (int) row[0];
            String broker_name = (String) row[1];
            Long total_claims = (Long) row[2];

            StringBuilder claimJson = new StringBuilder();
            claimJson.append("{")
                    .append("\"broker_id\":").append(broker_id).append(",")
                    .append("\"broker_name\":\"").append(broker_name).append("\",")
                    .append("\"total_claims\":\"").append(total_claims).append("\"")
                    .append("}");

            claimJoiner.add(claimJson.toString());
        }

        json.append(claimJoiner.toString()).append("]");
        return json.toString();
    }

    public String convertToJsonClaimsRate(List<Object[]> claimsRate) {
        StringBuilder json = new StringBuilder("[");
        StringJoiner claimRateJoiner = new StringJoiner(",");

        for (Object[] row : claimsRate) {
            Long approved_count = ((Number) row[0]).longValue();
            Long rejected_count = ((Number) row[1]).longValue();
            Long total_claims = ((Number) row[2]).longValue();

            // Ensure that rates are stored as BigDecimal
            BigDecimal approval_rate = (BigDecimal) row[3]; // Change to BigDecimal
            BigDecimal rejection_rate = (BigDecimal) row[4]; // Change to BigDecimal

            // Format the rates to 2 decimal places and convert to String
            String approval_rate_str = approval_rate.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String rejection_rate_str = rejection_rate.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

            StringBuilder claimsRateJson = new StringBuilder();
            claimsRateJson.append("{")
                    .append("\"approved_count\":").append(approved_count.longValue()).append(",")
                    .append("\"rejected_count\":").append(rejected_count.longValue()).append(",")
                    .append("\"total_claims\":").append(total_claims.longValue()).append(",")
                    .append("\"approval_rate\":").append(approval_rate_str).append(",")
                    .append("\"rejection_rate\":").append(rejection_rate_str)
                    .append("}");

            claimRateJoiner.add(claimsRateJson.toString());
        }

        json.append(claimRateJoiner.toString()).append("]");
        return json.toString();
    }
}
