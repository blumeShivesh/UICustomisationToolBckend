package org.example.OrgCode;
import org.example.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgCodeService {

    private final OrgCodeRepository orgCodeRepository  ;
    @Autowired
    public OrgCodeService(OrgCodeRepository orgCodeRepository) {
        this.orgCodeRepository = orgCodeRepository;
    }
    public String saveOrgCode(OrgCode orgCode) {
        System.out.println("OrgCodeService.saveOrgCode: "+orgCode.getOrgCode());
        OrgCode  savedOrgCode = orgCodeRepository.save(orgCode);
        return savedOrgCode.getOrgCode();
    }
    public OrgCode getOrgCodeById(Long id) {
        return orgCodeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("OrgCode not found with id: " + id));
    }
    public List<OrgCode> getAllOrgcode(){
        return orgCodeRepository.findAll();
    }

    public void deleteOrgCode(Long id) {
        orgCodeRepository.deleteById(id);
    }

    public void deleteAllOrgCode() {
        orgCodeRepository.deleteAll();
    }
    public String updateOrgCode(Long id, OrgCode orgCode) {
        OrgCode existingOrgCode = orgCodeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("OrgCode not found with id: " + id));
        existingOrgCode.setOrgCode(orgCode.getOrgCode());
        return orgCodeRepository.save(existingOrgCode).getOrgCode();
    }
}
