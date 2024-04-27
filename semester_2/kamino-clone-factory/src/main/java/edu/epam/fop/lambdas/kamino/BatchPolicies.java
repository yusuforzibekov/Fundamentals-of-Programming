package edu.epam.fop.lambdas.kamino;

import edu.epam.fop.lambdas.kamino.equipment.Equipment;
import edu.epam.fop.lambdas.kamino.equipment.EquipmentFactory;

import java.util.Iterator;

public class BatchPolicies {

    public static BatchPolicy getCodeAwarePolicy(String codePrefix, int codeSeed) {
        return new BatchPolicy() {
            private int currentSeed = codeSeed;

            @Override
            public CloneTrooper[] formBatchOf(CloneTrooper base, int count) {
                CloneTrooper[] batch = new CloneTrooper[count];
                for (int i = 0; i < count; i++) {
                    String newCode = codePrefix + "-" + String.format("%04d", currentSeed++);
                    CloneTrooper newClone = KaminoFactory.growClone(newCode);
                    newClone.setNickname(base.getNickname());
                    newClone.setEquipment(base.getEquipment());
                    batch[i] = newClone;
                }
                return batch;
            }
        };
    }

    public static BatchPolicy addNicknameAwareness(Iterator<String> nicknamesIterator, BatchPolicy policy) {
        return (base, count) -> {
            CloneTrooper[] batch = new CloneTrooper[count];
            for (int i = 0; i < count; i++) {
                String nickname = null;
                if (nicknamesIterator.hasNext()) {
                    nickname = nicknamesIterator.next();
                }
                CloneTrooper newClone = KaminoFactory.growClone(base.getCode());
                newClone.setNickname(nickname);
                CloneTrooper[] generatedBatch = policy.formBatchOf(newClone, 1);
                batch[i] = generatedBatch[0];
            }
            return batch;
        };
    }

    public static BatchPolicy addEquipmentOrdering(Equipment equipmentExample, BatchPolicy policy) {
        return (base, count) -> {
            CloneTrooper[] batch = new CloneTrooper[count];
            for (int i = 0; i < count; i++) {
                CloneTrooper newClone = KaminoFactory.growClone(base.getCode());
                Equipment orderedEquipment = EquipmentFactory.orderTheSame(equipmentExample);
                newClone.setEquipment(orderedEquipment);
                CloneTrooper[] generatedBatch = policy.formBatchOf(newClone, 1);
                batch[i] = generatedBatch[0];
            }
            return batch;
        };
    }

    public interface BatchPolicy {
        CloneTrooper[] formBatchOf(CloneTrooper base, int count);
    }
}