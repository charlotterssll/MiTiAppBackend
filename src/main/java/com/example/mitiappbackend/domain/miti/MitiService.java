/*
 * Copyright \(C\) open knowledge GmbH\.
 *
 * Licensed under the Apache License, Version 2\.0 \(the "License"\);
 * you may not use this file except in compliance with the License\.
 * You may obtain a copy of the License at
 *
 *     http://www\.apache\.org/licenses/LICENSE-2\.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied\.
 * See the License for the specific language governing permissions and
 * limitations under the License\.
 */
package com.example.mitiappbackend.domain.miti;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mitiappbackend.domain.user.UserRepository;
import com.example.mitiappbackend.infrastructure.exceptions.EmployeeNotRegisteredException;
import com.example.mitiappbackend.infrastructure.exceptions.MitiCatchMoreThanFiveEmployees;
import com.example.mitiappbackend.infrastructure.exceptions.MitiCatchOnSameDayException;
import com.example.mitiappbackend.infrastructure.exceptions.MitiNotFoundException;

@Service
public class MitiService {

    private static final int EMPLOYEE_LIST_SIZE_ONE = 1;
    private static final int EMPLOYEE_LIST_SIZE_TWO = 2;
    private static final int EMPLOYEE_LIST_SIZE_THREE = 3;
    private static final int EMPLOYEE_LIST_SIZE_FOUR = 4;
    private static final int EMPLOYEE_LIST_SIZE_FIVE = 5;

    @Autowired
    private MitiRepository mitiRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createMiti(Miti miti) throws
            MitiCatchOnSameDayException,
            EmployeeNotRegisteredException,
            MitiCatchMoreThanFiveEmployees {
        List<Miti> mitiRead = mitiRepository.readMiti();
        List<String> mitiInfos = mitiRead.stream()
            .map(Miti::catchMitiOnSameDay)
            .toList();
        if (mitiInfos.contains(miti.catchMitiOnSameDay())) {
            throw new MitiCatchOnSameDayException();
        } else if (miti.getEmployeeParticipants().size() == EMPLOYEE_LIST_SIZE_ONE
            && !userRepository.existsByUsername(miti.getEmployeeParticipants().get(0).getAbbreviation().getValue())) {
            throw new EmployeeNotRegisteredException();
        /*} else if (miti.getEmployee().size() == EMPLOYEE_LIST_SIZE_TWO
            && !userRepository.existsByUsername(miti.getEmployee().get(0).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(1).getAbbreviation().getValue())) {
            throw new EmployeeNotRegisteredException();
        } else if (miti.getEmployee().size() == EMPLOYEE_LIST_SIZE_THREE
            && !userRepository.existsByUsername(miti.getEmployee().get(0).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(1).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(2).getAbbreviation().getValue())) {
            throw new EmployeeNotRegisteredException();
         } else if (miti.getEmployee().size() == EMPLOYEE_LIST_SIZE_FOUR
            && !userRepository.existsByUsername(miti.getEmployee().get(0).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(1).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(2).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(EMPLOYEE_LIST_SIZE_THREE).getAbbreviation().getValue())) {
            throw new EmployeeNotRegisteredException();
       } else if (miti.getEmployee().size() == EMPLOYEE_LIST_SIZE_FIVE
            && !userRepository.existsByUsername(miti.getEmployee().get(0).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(1).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(2).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(EMPLOYEE_LIST_SIZE_THREE).getAbbreviation().getValue())
            || !userRepository.existsByUsername(miti.getEmployee().get(EMPLOYEE_LIST_SIZE_FOUR).getAbbreviation().getValue())) {
            throw new EmployeeNotRegisteredException();*/
        } else if (miti.getEmployeeParticipants().size() > EMPLOYEE_LIST_SIZE_FIVE) {
            throw new MitiCatchMoreThanFiveEmployees();
        } else {
            mitiRepository.createMiti(miti);
        }
    }

    @Transactional
    public List<Miti> readMiti() {
        return mitiRepository.readMiti();
    }

    @Transactional
    public Miti readMitiById(Long mitiId) throws MitiNotFoundException {
        Miti mitiRead = mitiRepository.readMitiById(mitiId);

        if (mitiRead == null) {
            throw new MitiNotFoundException(mitiId);
        }

        return mitiRepository.readMitiById(mitiId);
    }

    @Transactional
    public void updateMitiById(Long mitiId, Miti miti) {
        Miti mitiToUpdate = mitiRepository.readMitiById(mitiId);
        mitiToUpdate.setPlace(miti.getPlace());
        mitiToUpdate.setTime(miti.getTime());
        mitiToUpdate.setDate(miti.getDate());
    }

    @Transactional
    public void deleteMitiById(Long mitiId) throws MitiNotFoundException {
        Miti mitiDelete = mitiRepository.readMitiById(mitiId);

        if (mitiDelete == null) {
            throw new MitiNotFoundException(mitiId);
        }

        mitiRepository.deleteMitiById(mitiId);
    }
}
