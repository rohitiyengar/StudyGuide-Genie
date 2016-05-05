package visualization.models;

import java.util.ArrayList;
import java.util.List;

public class OriginalityForStudents {
	
	private List<Student> students;

	public List<Student> getStudents() {
		if(students == null)
		{
			students = new ArrayList<Student>();
		}
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
