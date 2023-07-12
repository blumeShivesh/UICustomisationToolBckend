package org.example.OrgCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orgCode")
@CrossOrigin(origins = "http://localhost:3000")
public class OrgCodeController {
    @Autowired
    private  OrgCodeService orgCodeService;

    @PostMapping
    public ResponseEntity<String> saveOrgCode(@RequestBody OrgCode orgCode) {
        String savedOrgCode = orgCodeService.saveOrgCode(orgCode);
        System.out.println("OrgCodeController.saveOrgCode: " + orgCode.getOrgCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrgCode);
    }
    @GetMapping
    public ResponseEntity<List<OrgCode>> getOrgCode() {

        List<OrgCode> orgCodeList = orgCodeService.getAllOrgcode();

        return ResponseEntity.ok(orgCodeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgCode> getOrgCodeById(@PathVariable("id") Long id){
        OrgCode orgCode = orgCodeService.getOrgCodeById(id);
        return ResponseEntity.ok(orgCode);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrgCode(@PathVariable("id") Long id) {
        orgCodeService.deleteOrgCode(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllOrgCode() {
        System.out.println("OrgCodeController.deleteAllOrgCode: ");
        orgCodeService.deleteAllOrgCode();
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateOrgCode(@PathVariable("id") Long id, @RequestBody OrgCode orgCode) {
        System.out.println("OrgCodeController.OrgCode: "+orgCode.getOrgCode());
        String updatedOrgCode = orgCodeService.updateOrgCode(id, orgCode);
        return ResponseEntity.ok(updatedOrgCode);
    }




}
