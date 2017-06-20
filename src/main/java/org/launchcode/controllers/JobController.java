package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        Job jobById = jobData.findById(id);
        model.addAttribute("job", jobById);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("jobForm", jobForm);
            return "new-job";
        }
        else {
            String jobName = jobForm.getName();
            Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
            Location location = jobData.getLocations().findById(jobForm.getLocationId());
            PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionId());
            CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
            Job job = new Job(jobName, employer, location, positionType, coreCompetency);
            jobData.add(job);
            return "redirect:/job?id=" + job.getId();
        }


    }
}
