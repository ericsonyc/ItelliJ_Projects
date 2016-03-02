#include<iostream>
#include<time.h>
#include <stdlib.h>

using namespace std;
#define NETSIZE 15
#define DIM 10
#define POPSIZE 15
#define VISUAL 10

double cp[14][2]={16.47,96.10,
                  16.47,94.44,
                  20.09,92.54,
                  22.39,93.37,
                  25.23,97.24,
                  22.00,96.05,
                  20.47,97.02,
                  17.20,96.29,
                  16.30,97.38,
                  14.05,98.12,
                  16.53,97.38,
                  21.52,95.59,
                  19.41,97.13,
                  20.09,92.55};
double Path[NETSIZE][NETSIZE];
void InitPath()
{
    for(int i=0;i<NETSIZE;i++)
        for(int j=0;j<NETSIZE;j++)
            Path[i][j]=(cp[i][0]-cp[j][0])*(cp[i][0]-cp[j][0])+(cp[i][1]-cp[j][1])*(cp[i][1]-cp[j][1]);
}

class Artificial_fish
{
public:

    Artificial_fish();
    //float prey();
    void updateneiborflag(Artificial_fish *Pointer);

    // float follow();
    // float swarm();
    void evaluate();
    void ShuiJi();
    void display();
    void display1();
    int fish[DIM];
    bool neiborflag[POPSIZE];
    double fitness;
};
Artificial_fish::Artificial_fish()
{
    this->ShuiJi();
    for(int flag=0;flag<POPSIZE;flag++)
        neiborflag[flag]=false;
}
void Artificial_fish::ShuiJi()
{

    int i,num;
    bool *tag=new bool[DIM];
    for(i=0;i<DIM;i++)
        tag[i]=false;
    while(--i>=0)
    {
        do{
            num=rand()%DIM;
        }while(tag[num]);
        fish[i]=num;
        tag[num]=true;
    }
    delete []tag;

}
void Artificial_fish::display()
{
    for(int i=0;i<DIM;i++)
    {
        cout<<fish[i]<<" ";
    }
    cout<<endl;
    cout<<"fitness="<<fitness;
    cout<<endl;

}

void Artificial_fish::display1()
{
    for(int j=0;j<POPSIZE-1;j++)
    {
        cout<<neiborflag[j];
    }
    cout<<endl;
}


void Artificial_fish::updateneiborflag(Artificial_fish * Pointer)
{
    for(int flag=0;flag<POPSIZE;flag++)
        neiborflag[flag]=false;

    for(int n=0;n<POPSIZE;n++)
    {
        int distance=0;
        for(int i=0;i<NETSIZE-1;i++)
        {
            if(this->fish[i]!=(Pointer+n)->fish[i])
                distance++;
        }

        if(distance<=VISUAL&&distance!=0)
        {
            neiborflag[n]=true;
        }

    }
}

void Artificial_fish::evaluate()//i条鱼状态变量，edge是边长度矩阵，返回一个回路的真实长度
{
    fitness=0;
    for(int m=0;m<DIM-1;m++)
        fitness+=Path[this->fish[m]][this->fish[m+1]];
    fitness+=Path[this->fish[0]][this->fish[DIM-1]];
}
int main()
{

    srand((unsigned)time(NULL)); //初始化鱼群随机产生的
    InitPath();

    Artificial_fish a[POPSIZE];

    int i;
    for(i=0;i<POPSIZE;i++)
    {
        a[i].evaluate();
        cout<<"The "<<i<<"th fish:";

        (a+i)->display();
    }

    for(i=0;i<POPSIZE;i++)
    {
        (a+i)->updateneiborflag(a);
        (a+i)->display1();
    }
    return 0;
}
