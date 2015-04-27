package se.sael.listexample.listexample;

public class Checkbox
{
    String checkboxText = null; //code
    String checkBox = null; //name
    boolean selected = false;
    int position;

    public Checkbox(String checkboxText, String checkBox, boolean selected, int position){
        this.checkboxText = checkboxText;
        this.checkBox = checkBox;
        this.selected = selected;
        this.position = position;
    }

    public String getCheckboxText() {
        /*if(selected){
            checkboxText = "Avklarat!";
        }else{
            checkboxText = "Avklarat?";
        }*/
        return checkboxText;
    }
    public void setCheckboxText(String checkboxText) {
        this.checkboxText = checkboxText;
    }
    public String getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(String checkBox) {
        this.checkBox = checkBox;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getPosition() {
        return position;
    }
}