package ListException;



public class ListException extends RuntimeException
{


    private int index;

        public ListException(String message, int index)
        {

            super(message);
            this.index = index;

        }


    private int start;
    private int end;

    public ListException(String message, int start, int end)
    {

        super(message);
        this.start = start;
        this.end = end;

    }


    @Override
    public String toString()
    {
        if (index > 0) {

            return getMessage() + ". index = " + index + ";  ";

        }else{

            return  getMessage() + ". start = " + start + ";  " + ". end = " + end + ";  ";

        }

    }



}