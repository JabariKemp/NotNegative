package com.dev.jabarik.notnegative;

import android.app.job.JobParameters;
import android.app.job.JobService;



/**
 * Created by Jabari on 12/8/2017.
 */

public class MJobScheduler extends JobService {



    public boolean onStartJob(final JobParameters jobParameters) {

;
        return true;
    }




    @Override
    public boolean onStopJob(JobParameters jobParameters) {



        return false;

    }
}
