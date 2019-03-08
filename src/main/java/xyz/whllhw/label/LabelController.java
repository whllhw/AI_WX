package xyz.whllhw.label;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.whllhw.util.CommonResponseForm;
import xyz.whllhw.util.SessionUtil;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class LabelController {

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private LabelService labelService;

    @PostMapping("/label/add")
    public CommonResponseForm addLabel(@RequestBody List<String> labelList) {
        labelService.addLabel(SessionUtil.getUserName(httpSession), labelList);
        return CommonResponseForm.of204("added");
    }

    @GetMapping("/label/get")
    public CommonResponseForm getLabel() {
        return CommonResponseForm.of200("got",
                labelService.getLabel(SessionUtil.getUserName(httpSession)));
    }

}
