package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

  private final ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
    return Arrays.asList(projects)
            .stream()
            .map((m) -> new Project()
                    .withId(m.getId()
                            .intValue())
                    .withName(m.getName()))
            .collect(Collectors.toSet())
            ;
  }

  public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator()
            .getMantisConnectPort(new URL(app.getProperty("soap.portAddress")));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories("administrator", "root",
            BigInteger.valueOf(issue
                    .getProject()
                    .getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setCategory(categories[0]);
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
    IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);
    return new Issue()
            .withSummary(createdIssueData.getSummary())
            .withDescription(createdIssueData.getDescription())
            .withId((createdIssueData.getId()
                    .intValue()))
            .withProject(new Project()
                    .withId(createdIssueData.getProject().getId()
                            .intValue())
                    .withName(createdIssueData.getProject().getName()));
  }

  public String getBugStatus(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData createdIssueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
    // вывод на консоль членов перечисления статусов багрепората:
//    ObjectRef[] result = mc.mc_enum_status("administrator", "root");// - perechislenie
//    Arrays.stream(result).map(objectRef -> objectRef.getName()).forEach(System.out::println);
    ObjectRef status = createdIssueData.getStatus(); //ObjectRef объект со String ( имя ) и BigInteger( id [это не issueId] ).
    return status.getName(); // можно было записать в одну  строку, но я хотел визуализировать класс ObjectRef .
  }
}
