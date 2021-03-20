package ListException;



public class ListException extends Exception
{


    private int index;

        public ListException(String message, int index)
        {

            super(message);
            this.index = index;

        }



    @Override
    public String toString()
    {

        return  getMessage() + ". index = " + index + ";  ";

    }

}