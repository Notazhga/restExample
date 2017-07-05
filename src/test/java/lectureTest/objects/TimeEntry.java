package lectureTest.objects;

import java.util.HashMap;
import java.util.Map;


public class TimeEntry {
    private Map<String, Integer> time_entry;

    public TimeEntry() {

        time_entry = new HashMap<>();
    }



    public void setIssueId(int id){

        time_entry.put("issue_id", id);
    }

    public void setHours(int hours){

        time_entry.put("hours", hours);
    }

    public void setActivityId(int id){

        time_entry.put("activity_id", id);
    }

    public void setawdwd(int id){

        time_entry.put("activity_id", id);
    }

}