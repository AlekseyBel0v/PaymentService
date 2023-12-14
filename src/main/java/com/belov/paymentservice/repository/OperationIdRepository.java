package com.belov.paymentservice.repository;

import com.belov.paymentservice.RequestBody.TransferParam;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OperationIdRepository {
    //мапа хранит: ключ - id транзакции / значение - параметры транзакции
    private static ConcurrentHashMap<Long, TransferParam> operationIdRepository = new ConcurrentHashMap<>();
    private static AtomicLong lastId = new AtomicLong(1);

    public static long createNewIdOperation(TransferParam transferParam) {
        long newId = lastId.getAndIncrement();
        operationIdRepository.put(newId, transferParam);
        return newId;
    }

    public static TransferParam getTransferParam(long idOperation) {
        return operationIdRepository.get(idOperation);
    }
}
