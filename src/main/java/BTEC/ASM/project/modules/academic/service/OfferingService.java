package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.OfferingRequest;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;

import java.util.List;
import java.util.Optional;

public interface OfferingService {

    Optional<OfferingResponse> create(OfferingRequest request);

    List<OfferingResponse> getAll();

    Optional<OfferingResponse> getById(Long id);

    Optional<OfferingResponse> update(Long id, OfferingRequest request);

    boolean delete(Long id);
}
