package xyz.whllhw.judge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.whllhw.judge.form.JudgeForm;
import xyz.whllhw.judge.form.MachineJudge;
import xyz.whllhw.util.CommonResponseForm;
import xyz.whllhw.util.SessionUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@Slf4j
public class JudgeController {

    @Autowired
    private TaskJudgeService taskJudgeService;
    @Autowired
    private HttpSession httpSession;

    /**
     * 获取指定标签需要机器审核的数据
     *
     * @param label 标签
     */
    @GetMapping("/judge/machine")
    public CommonResponseForm getMachineJudge(@RequestParam("label") String label) {
        return CommonResponseForm.of200("ok", taskJudgeService.getMachineJudge(label));
    }

    /**
     * 机器提交数据
     */
    @PostMapping("/judge/machine")
    public CommonResponseForm machineJudge(@RequestBody MachineJudge form) {
        taskJudgeService.machineJudge(form.getDataId(), form.getLabel(), form.getScore());
        return CommonResponseForm.of204("ok");
    }

    /**
     * 用户提交标注信息
     * 需提供taskId
     */
    @PostMapping("/judge/user")
    public CommonResponseForm userJudge(@RequestBody JudgeForm form) {
        taskJudgeService.submitJudgeFromUser(form.getTaskId(), form.getScore(), SessionUtil.getUserName(httpSession));
        return CommonResponseForm.of204("ok");
    }

    /**
     * 管理员提交标注信息
     * 直接提供dataId
     */
    @PostMapping("/judge/admin")
    public CommonResponseForm adminJudge(@RequestBody JudgeForm form) {
        taskJudgeService.submitFromAdmin(form.getDataId(), form.getScore());
        return CommonResponseForm.of204("ok");
    }

    @GetMapping("/judge/admin")
    public CommonResponseForm getAdminJudge() {
        return CommonResponseForm.of200("ok", taskJudgeService.getAdminJudge());
    }

    /**
     * 获取数据，提供任务id
     */
    @GetMapping("/judge/file/{taskId}")
    public void sendFile(@PathVariable("taskId") Long taskId, HttpServletResponse httpServletResponse) throws IOException {
//        log.info(httpServletRequest.getContextPath());
//        String taskId = httpServletRequest.getContextPath().split("/")[2];
        httpServletResponse.setHeader("content-type", "audio/x-wav");
        boolean isAdmin = SessionUtil.isAdmin(httpSession);
        try (InputStream out = isAdmin ? taskJudgeService.getFileByDataId(taskId)
                : taskJudgeService.getJudgeFile(taskId);
             OutputStream os = httpServletResponse.getOutputStream()) {
            byte[] buff = new byte[1024];
            int code = out.read(buff);
            while (code != -1) {
                os.write(buff);
                os.flush();
                code = out.read(buff);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            httpServletResponse.sendError(404, "file not found");
        }
    }

}
