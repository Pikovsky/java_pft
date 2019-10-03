package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion(){
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectContact(0);
    app.getContactHelper().deleteSelectedContact();
    app.getContactHelper().acceptDeletionContact();
    //app.getContactHelper().closeAlertAndGetItsText();
    app.getNavigationHelper().gotoHomePage();
  }


}
