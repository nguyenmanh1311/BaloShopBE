package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.payment.MomoPayment;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.PaymentService;
import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.LogUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public ResponseEntity<?> createNewPayment(MomoPayment request) {
        LogUtils.init();
        Environment environment = Environment.selectEnv("dev");
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        String notifyURL = "https://google.com.vn";
        try {
            PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment,
                    orderId,
                    requestId,
                    Long.toString(request.getAmount()),
                    request.getOrderInfo(),
                    request.getReturnUrl(),
                    notifyURL,
                    request.getExtraData(),
                    RequestType.CAPTURE_WALLET,
                    Boolean.TRUE);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Lấy link thanh toán thành công!", captureWalletMoMoResponse.getPayUrl()));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "Hệ thống thanh toán quá tải!", null));
        }
    }
}