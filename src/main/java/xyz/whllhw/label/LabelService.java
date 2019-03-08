package xyz.whllhw.label;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelService {
    @Autowired
    private LabelRepository labelRepository;

    @Transactional(rollbackFor = Exception.class)
    public void addLabel(String user, List<String> list) {
        list.forEach(
                s -> {
                    LabelUserEntity labelUserEntity = new LabelUserEntity();
                    labelUserEntity.setUser(user);
                    labelUserEntity.setVal(s);
                    labelRepository.save(labelUserEntity);
                }
        );
    }

    public List<String> getLabel(@NonNull String user) {
        return labelRepository.findAllByUser(user).stream().map(
                LabelUserEntity::getVal).collect(Collectors.toList());
    }

}
