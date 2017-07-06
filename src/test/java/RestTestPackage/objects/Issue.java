package RestTestPackage.objects;

import java.util.HashMap;
import java.util.Map;


public class Issue {
    private Map<String, String> issue;

    public Issue() {

        issue = new HashMap<>();
    }

    public void setProjectId(String Id){ // проект автоматизации айди 2071
        issue.put("project_id", Id);
    }

    public void setSubject(String subject){     //название бага
        issue.put("subject", subject);
    }
    public void setDescription(String description){    //описание бага
        issue.put("description", description);
    }

    public void setWatcherId(String Id){        //вотчер, айди 104
        issue.put("watcher_user_ids", Id);
    }

    public void setAssignedId(String Id) {      //асигн ту , айди 104
        issue.put("assigned_to_id", Id);
    }

    public void setPriorityId(String Id){       // урджент айди 6
        issue.put("priority_id", Id);
    }

    public void setTrackerId(String Id){        // баг айди 3
        issue.put("tracker_id", Id);
    }

    public void setDoneRatio(String DoneRatio) {       // 50
        issue.put("done_ratio", DoneRatio);
    }
}
