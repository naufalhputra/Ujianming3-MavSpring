package com.juarakoding.controller;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juarakoding.model.Datainfo;
import com.juarakoding.model.DatainfoRM;
import com.juarakoding.model.Worker;
import com.juarakoding.model.WorkerRM;

@RestController
@RequestMapping("/worker")
public class workercontroller {
	
	@Autowired
	JdbcTemplate jdbc;

	public List<Worker> getWorker() {
		String sql = "SELECT * FROM Worker ";

		List<Worker> worker = jdbc.query(sql, new WorkerRM());
		
		return worker;
		

	}
	
	public int insertWorker(Worker worker) {
		return jdbc.update("insert into worker(worker_id,first_name,last_name,salary,joining_date,department) values ('" + worker.getWorker_id() + "','"
				+ worker.getFirst_name() + "','" + worker.getLast_name() + "'," + worker.getSalary() + ",'" + worker.getJoining_date() + "','" + worker.getDepartment() + "')");

	}

	
	public int updateWorker(String worker_id, Worker worker) {

		return jdbc.update("UPDATE worker SET `first_name`='" + worker.getFirst_name() + "',`last_name`='" + worker.getLast_name()
				+ "',`salary`=" + worker.getSalary() + ",`joining_date`='" + worker.getJoining_date() + "',`department`='" + worker.getDepartment() + "' WHERE worker_id = '" + worker.getWorker_id() + "'");

	}

	public int deleteWorker(String worker_id) {
		return jdbc.update("DELETE FROM `worker`  WHERE `worker_id` = '" + worker_id + "';");
		
	
	}
	
	public List<Worker> getWorkerSalary() {
		
		String sql = "select * from worker order by Salary desc limit 5";
		
		List <Worker> worker = jdbc.query(sql,new WorkerRM());
		
		return worker;
		
	}
	
	public List<Worker> getWorkerIdentikSalary() {
		
		String sql = "Select * from worker where Salary in (select Salary from worker group by Salary having ( count(Salary) > 1 ))";
		
		List <Worker> worker = jdbc.query(sql,new WorkerRM());
		
		return worker;
		
	}
	
	public List<Datainfo> getDatainfo() {
		
		String sql = "CALL Number_Of_Workers()";
		
		List <Datainfo> deptinfo = jdbc.query(sql,new DatainfoRM());
		
		return deptinfo;
		
	}

	@GetMapping("/datainfo")
	public List<Datainfo> listDept(){
		
		return getDatainfo();
		
	}
	
	@GetMapping("/identiksalary")
	public List<Worker> listIdentikSalary
	(){
		
		return getWorkerIdentikSalary();
		
	}
	
	@GetMapping("/datasalary")
	public List<Worker> listdatasalary(){
		
		return getWorkerSalary();
		
	}

	
	@GetMapping("/")
    public List<Worker> list(){

        return getWorker();

    }
	
	 @PostMapping("/")
	    public String add(@RequestBody Worker worker) {
		 

			if (this.insertWorker(worker) == 1) {
				return "Insert data berhasil";
			} else {
				return "Insert data gagal";
			}
	    }
	
	
	 @PutMapping("/{worker_id}")
	    public ResponseEntity<?> update(@RequestBody Worker worker, @PathVariable String worker_id) {
		 try {
	            updateWorker(worker_id, worker);
	            return new ResponseEntity<>(HttpStatus.OK);
	        } catch (NoSuchElementException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
		 
	 }
	 
	 @DeleteMapping("/{worker_id}")
	    public void delete(@PathVariable String worker_id) {
		 	deleteWorker(worker_id);
	 }
	
}
