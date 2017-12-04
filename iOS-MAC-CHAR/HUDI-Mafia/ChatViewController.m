//
//  ChatViewController.m
//  HUDI-Mafia
//
//  Created by YongJai on 2017. 4. 14..
//  Copyright © 2017년 YongJai. All rights reserved.
//

#import "ChatViewController.h"
#import "ChatTableViewCell.h"
#import "UserCollectionViewCell.h"
#import <QuartzCore/QuartzCore.h>

@interface ChatViewController () <UITableViewDataSource, UITableViewDelegate, UICollectionViewDelegate, UICollectionViewDataSource>

@property (weak, nonatomic) IBOutlet UITextField *chatTextField;
@property (weak, nonatomic) IBOutlet UIButton *plus;
@property (weak, nonatomic) IBOutlet UILabel *roomTitleLabel;
@property (weak, nonatomic) IBOutlet UILabel *roomNumLabel;
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;
@property (weak, nonatomic) IBOutlet UILabel *yourRole;

@end

@implementation ChatViewController

- (void) viewDidLoad {
    [self sendAccessRoom];
    [self subscribeAccessRoom];
    [self subscribeMsg];
    [self subscribeGetReady];
    [self subscribeVote];
    [self subscribeInvest];

    _chatContent = [[NSMutableArray alloc]init];
    _chatNick = [[NSMutableArray alloc]init];
    _userReady = [[NSMutableArray alloc]init];
    
    self.plus.layer.cornerRadius = 0.5 * _plus.bounds.size.width;
    self.sendBtn.layer.cornerRadius = 6.0f;
    
    _roomTitleLabel.text = _roomTitle;
    _roomNumLabel.text = [NSString stringWithFormat:@"NO.%@", _roomNum];
    [super viewDidLoad];
}


- (void) viewWillAppear:(BOOL)animated {
    [_chatTable reloadData];
    [_userCollection reloadData];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


//*  여기부터 코드가 정말... 답이 없습니다ㅎㅎ  *//
//*  Websocket을 이용하는 메소드들을 클래스를 하나 만들어서 빼든지 해야할 것 같습니다.  *//

- (void) sendMsg {
    NSString *nickName = @"yongjai";
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];

    NSString *textMessage = [NSString stringWithFormat:@"%@", _chatTextField.text];

    NSDictionary *msgDict = @{
                              @"userName" : nickName,
                              @"content": textMessage
                              };
    
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               NSData *data = [NSJSONSerialization dataWithJSONObject:msgDict options:0 error:&error];
               NSString *body =[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
               [client sendTo:[NSString stringWithFormat:@"/to/chat/%@", _roomNum] body:body];
           }];
}


- (void) subscribeMsg {
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               [client subscribeTo:[NSString stringWithFormat:@"/from/chat/%@", _roomNum]messageHandler:^(STOMPMessage *message){
                   NSLog(@"got message %@", message.body);
                   NSData *jsonData = [message.body dataUsingEncoding:NSUTF8StringEncoding];
                   _chatMessage = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:nil];
                   NSLog(@"%@", _chatMessage);
                   [_chatNick addObject:[_chatMessage valueForKey:@"userName"]];
                   [_chatContent addObject:[_chatMessage valueForKey:@"content"]];
                   [self viewWillAppear:YES];
               }];
           }];
}


- (void) sendGetReady {
    NSString *nickName = @"yongjai";
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    NSDictionary *msgDict = @{
                              @"userName" : nickName
                              };
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               NSData *data = [NSJSONSerialization dataWithJSONObject:msgDict options:0 error:&error];
               NSString *body =[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
               [client sendTo:[NSString stringWithFormat:@"/to/ready/%@", _roomNum] body:body];
               UserCollectionViewCell *cell;
               cell.userReady.hidden = NO;
           }];
}


- (void) subscribeGetReady {
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               [client subscribeTo:[NSString stringWithFormat:@"/from/ready/%@", _roomNum]messageHandler:^(STOMPMessage *message){
                   NSLog(@"got message %@", message.body);
                   NSData *jsonData = [message.body dataUsingEncoding:NSUTF8StringEncoding];
                   NSDictionary *Message = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:nil];
                   if([[Message valueForKey:@"startTimer"]boolValue] == true) {
                       NSLog(@"sendgameStart");
                       [self sendGameStart];
                       [self subscribeGameStart];
                   }
                   [self viewWillAppear:YES];
               }];
           }];
}


- (void) sendAccessRoom {
    NSString *nickName = @"yongjai";
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    NSDictionary *msgDict = @{
                              @"userName" : nickName,
                              @"access" : @"enter"
                              };
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               NSData *data = [NSJSONSerialization dataWithJSONObject:msgDict options:0 error:&error];
               NSString *body =[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
               [client sendTo:[NSString stringWithFormat:@"/to/access/%@", _roomNum] body:body];
               NSLog(@"헤헤헤헤헿헤헤");
               NSLog(@"헤헤헤");
               [self viewWillAppear:YES];
           }];
}


- (void) sendExitRoom {
    NSString *nickName = @"yongjai";
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    NSDictionary *msgDict = @{
                              @"userName" : nickName,
                              @"access" : @"exit"
                              };
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               NSData *data = [NSJSONSerialization dataWithJSONObject:msgDict options:0 error:&error];
               NSString *body =[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
               [client sendTo:[NSString stringWithFormat:@"/to/access/%@", _roomNum] body:body];
           }];
}


- (void) subscribeAccessRoom {
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               [client subscribeTo:[NSString stringWithFormat:@"/from/access/%@", _roomNum]messageHandler:^(STOMPMessage *message){
                   NSLog(@"got message %@", message.body);
                   NSData *jsonData = [message.body dataUsingEncoding:NSUTF8StringEncoding];
                   NSDictionary *Message = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:nil];
                   NSLog(@"access Room = %@", Message);
                   _accessUserInfo = [[NSMutableArray alloc]init];
                   _accessUserInfo = [Message valueForKey:@"users"];
                   [self viewWillAppear:YES];
               }];
           }];
}


- (void) sendGameStart {
    NSString *nickName = @"yongjai";
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    NSDictionary *msgDict = @{
                              @"userName" : nickName
                              };
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               NSData *data = [NSJSONSerialization dataWithJSONObject:msgDict options:0 error:&error];
               NSString *body =[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
               [client sendTo:[NSString stringWithFormat:@"/to/gameStart/%@/%@", _roomNum, nickName] body:body];
           }];
}



- (void) subscribeGameStart {
    NSString *nickName = @"yongjai";
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    [self startTimer];
    
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               [client subscribeTo:[NSString stringWithFormat:@"/from/gameStart/%@/%@", _roomNum, nickName]messageHandler:^(STOMPMessage *message){
                   NSLog(@"got message %@", message.body);
                   _yourRole.text = [NSString stringWithFormat:@"당신의 직업은 %@ 입니다.", message.body];
               }];
           }];
}


- (void) sendVote {
    NSString *nickName = @"yongjai";
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    NSDictionary *msgDict = @{
                              @"userName" : nickName,
                              @"votedUser" : _votedNick[0],
                              @"stage" : @"day"
                              };
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               NSData *data = [NSJSONSerialization dataWithJSONObject:msgDict options:0 error:&error];
               NSString *body =[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
               [client sendTo:[NSString stringWithFormat:@"/to/vote/%@", _roomNum] body:body];
           }];
}


- (void) subscribeVote {
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               [client subscribeTo:[NSString stringWithFormat:@"/from/vote/%@", _roomNum]messageHandler:^(STOMPMessage *message){
                   NSLog(@"got message %@", message.body);
                   NSData *jsonData = [message.body dataUsingEncoding:NSUTF8StringEncoding];
                   NSDictionary *Message = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:nil];
                   NSLog(@"access Room = %@", Message);
               }];
           }];
}


- (void) sendInvest {
    NSString *nickName = @"yongjai";
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               [client subscribeTo:[NSString stringWithFormat:@"/to/invest/%@/%@", _roomNum, nickName]messageHandler:^(STOMPMessage *message){
                   NSLog(@"got message %@", message.body);
                   NSData *jsonData = [message.body dataUsingEncoding:NSUTF8StringEncoding];
                   NSDictionary *Message = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:nil];
                   NSLog(@"access Room = %@", Message);
               }];
           }];
}



- (void) subscribeInvest {
    NSString *nickName = @"yongjai";
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://211.249.60.54:80/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               [client subscribeTo:[NSString stringWithFormat:@"/from/invest/%@/%@", _roomNum, nickName]messageHandler:^(STOMPMessage *message){
                   NSLog(@"got message %@", message.body);
                   NSData *jsonData = [message.body dataUsingEncoding:NSUTF8StringEncoding];
                   NSDictionary *Message = [NSJSONSerialization JSONObjectWithData:jsonData options:kNilOptions error:nil];
                   NSLog(@"access Room = %@", Message);
               }];
           }];
}

//방에 한명만 남았을 때 그 사람이 나가면 방을 없애주기
//- (void) exitRoom {
//    NSString *URLString = @"http://211.249.60.54:8000/api/room";
//    NSURL *URL = [NSURL URLWithString:URLString];
//    NSMutableURLRequest *exitRequest = [NSMutableURLRequest requestWithURL:URL];
//    
//    [exitRequest setValue:@"application/json" forHTTPHeaderField: @"content-Type"];
//    [exitRequest setHTTPMethod:@"DELETE"];
//    
//    NSHTTPURLResponse *exitResponse;
//    NSError *error;
//    
//    NSData *exitResultData = [NSURLConnection sendSynchronousRequest:exitResponse returningResponse:&exitRequest error:&error];
//    NSDictionary *dataDic = [NSJSONSerialization JSONObjectWithData:exitResultData options:NSJSONReadingMutableContainers error:NULL];
//    NSLog(@"exit Response = %@", dataDic);
//}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    [tableView setContentOffset:CGPointMake(0.0, tableView.contentSize.height - tableView.rowHeight)];
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [_chatContent count];
}


-(void)tableView:(UITableView *)tableView
 willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    [cell setBackgroundColor:[UIColor clearColor]];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *myCellIdentifier = @"myChatCell";
    static NSString *userCellIdentifier = @"userChatCell";
    
    ChatTableViewCell *cell;

    if ([[_chatNick objectAtIndex:indexPath.row]isEqualToString:@"yongjai"]) {
            cell = [tableView dequeueReusableCellWithIdentifier:myCellIdentifier forIndexPath:indexPath];
            cell.myChat.text = [_chatContent objectAtIndex:indexPath.row];
            cell.myNick.text = [_chatNick objectAtIndex:indexPath.row];
        } else {
            cell = [tableView dequeueReusableCellWithIdentifier:userCellIdentifier forIndexPath:indexPath];
            cell.usersChat.text = [_chatContent objectAtIndex:indexPath.row];
            cell.usersNick.text = [_chatNick objectAtIndex:indexPath.row];
        }
    return cell;
}


- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
    return 1;
}


- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return [_accessUserInfo count];
}


- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    UserCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"userCell" forIndexPath:indexPath];
    cell.userName.text = [[_accessUserInfo valueForKey:@"nickname"]objectAtIndex:indexPath.row];
    if ([[[_accessUserInfo valueForKey:@"ready"]objectAtIndex:indexPath.row]boolValue] == 1) {
        cell.userReady.hidden = NO;
    }
    return cell;
}


- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
    _votedNick = [[NSMutableArray alloc] init];
    UserCollectionViewCell *cell = (UserCollectionViewCell *)[collectionView cellForItemAtIndexPath:indexPath];
    cell.voteTarget.hidden = NO;
    _votedNick = [NSMutableArray arrayWithObject:cell.userName.text];
    NSLog(@"투표된 사람 이름 %@", _votedNick[0]);
}


- (void)collectionView:(UICollectionView *)collectionView didDeselectItemAtIndexPath:(NSIndexPath *)indexPath {
    UserCollectionViewCell *cell = (UserCollectionViewCell *)[collectionView cellForItemAtIndexPath:indexPath];
    cell.voteTarget.hidden = YES;
}


- (IBAction)clickedSendBtn:(id)sender {
    if ([_chatTextField.text isEqualToString:@""]) {
        _sendBtn.enabled = YES;
    } else {
        [self sendMsg];
        _chatTextField.text = @"";
    }
}


- (IBAction)clickedSendVoteBtn:(id)sender {
    [self sendVote];
    [self subscribeVote];
}


- (IBAction)clickedReadyBtn:(id)sender {
    [self sendGetReady];
}


- (IBAction)clickedExitRoom:(id)sender {
    [self sendExitRoom];
    [self subscribeAccessRoom];
}


- (void) timerRun {
    _count++;
    _secondsCount = _secondsCount - 1;
    int minuate = _secondsCount / 60;
    int seconds = _secondsCount - (minuate * 60);
    NSString *timerOutput = [NSString stringWithFormat:@"%2d:%.2d", minuate, seconds];
    _timeLabel.text = timerOutput;
    if (_count == 5) {
        [_countDownTimer invalidate];
        _countDownTimer = nil;
        _secondsCount = 60;
        [self keepTimer];
    } else if (_count == 65) {
        [_countDownTimer invalidate];
        _countDownTimer = nil;
        _secondsCount = 5;
        [self keepTimer];
    } else if (_count == 70) {
        [_countDownTimer invalidate];
        _countDownTimer = nil;
        _secondsCount = 30;
        [self keepTimer];
    } else if (_count == 100) {
        [_countDownTimer invalidate];
        _countDownTimer = nil;
        _secondsCount = 5;
        _count = 0;
        [self startTimer];
    }
}


- (void) startTimer {
    _secondsCount = 5;
    _countDownTimer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(timerRun) userInfo:nil repeats:YES];
}
- (void) keepTimer {
    _countDownTimer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(timerRun) userInfo:nil repeats:YES];
}

- (void) readyStatus {
    
}

@end
