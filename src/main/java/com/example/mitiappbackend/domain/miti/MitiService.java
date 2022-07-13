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
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mitiappbackend.domain.employee.Abbreviation;
import com.example.mitiappbackend.domain.place.Place;
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
    public Optional<Miti> readMitiById(Date date, Abbreviation employeeCreator) throws MitiNotFoundException {
        Optional<Miti> mitiRead = mitiRepository.readMitiById(date, employeeCreator);

        if (mitiRead == null) {
            throw new MitiNotFoundException(date, employeeCreator);
        }

        return mitiRepository.readMitiById(date, employeeCreator);
    }

    @Transactional
    public void updateMitiById(Date date, Abbreviation employeeCreator, Miti miti) {
        Optional<Miti> mitiToUpdate = mitiRepository.readMitiById(date, employeeCreator);
        mitiToUpdate.ifPresent(m -> m.setPlace(new Place(miti.getPlace().getLocality(),
            miti.getPlace().getLocation(), miti.getPlace().getStreet())));
        mitiToUpdate.ifPresent(m -> m.setTime(new Time(miti.getTime().getValue())));
        mitiToUpdate.ifPresent(m -> m.setDate(new Date(miti.getDate().getValue())));
    }

    @Transactional
    public void deleteMitiById(Date date, Abbreviation employeeCreator) throws MitiNotFoundException {
        Optional<Miti> mitiDelete = mitiRepository.readMitiById(date, employeeCreator);

        if (!mitiDelete.isPresent()) {
            throw new MitiNotFoundException(date, employeeCreator);
        }
        mitiRepository.deleteMitiById(date, employeeCreator);
    }
}
