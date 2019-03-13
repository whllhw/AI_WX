package xyz.whllhw.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.whllhw.util.CommonResponseForm;
import xyz.whllhw.util.SessionUtil;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskUserService taskUserService;

    @Autowired
    private HttpSession httpSession;

    /**
     * 查询所有任务
     */
    @GetMapping("/task")
    public CommonResponseForm getAllTask(@RequestParam("taskType") String taskType, @RequestParam("page") Integer page) {
        return CommonResponseForm.ofPageable("got", taskService.getAllTask(taskType, page));
    }

    /**
     * 查询单个任务
     */
    @GetMapping("/task/{taskId}")
    public CommonResponseForm getTask(@PathVariable(name = "taskId") Long id) {
        return CommonResponseForm.of200("got", taskService.getTask(id));
    }

    /**
     * 查询用户接受的所有任务
     *
     * @param state 任务状态
     */
    @GetMapping("/myTask")
    public CommonResponseForm getMyTask(@RequestParam(value = "state", required = false) String state) {
        return CommonResponseForm.of200("got",
                state == null ? taskUserService.getUserTask(SessionUtil.getUserName(httpSession))
                        : taskUserService.getUserTaskByState(SessionUtil.getUserName(httpSession), state));
    }

    /**
     * 接受任务
     */
    @PostMapping("/task/{taskId}")
    public CommonResponseForm takeTask(@PathVariable("taskId") Long id) {
        boolean flag = taskUserService.takeTask(id, SessionUtil.getUserName(httpSession));
        if (flag) {
            return CommonResponseForm.of204("ok");
        } else {
            return CommonResponseForm.of400("error");
        }
    }

    /**
     * 提交任务（数据进入待审核状态）
     */
    @PostMapping("/task/submit/{taskId}")
    public CommonResponseForm submit(@PathVariable("taskId") Long id, MultipartFile file) {
        try {
            Long code = taskUserService.submitTask(id, SessionUtil.getUserName(httpSession), file);
            if (code == -1) {
                return CommonResponseForm.of400("未接受任务");
            } else if (code == -2) {
                return CommonResponseForm.of400("重复提交");
            }
            return CommonResponseForm.of204("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResponseForm.of500("io error");
    }

    /**
     * 管理员发布任务
     */
    @PostMapping("/task/add")
    public CommonResponseForm addTask(@RequestBody TaskEntity taskEntity) {
        if (0 < taskService.addTask(taskEntity, 500)) {
            return CommonResponseForm.of204("added");
        } else {
            return CommonResponseForm.of400("failed");
        }
    }
}
