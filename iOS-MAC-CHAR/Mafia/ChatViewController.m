//
//  ChatViewController.m
//  Mafia
//
//  Created by YongJai on 2017. 3. 31..
//  Copyright © 2017년 YongJai. All rights reserved.
//

#import "ChatViewController.h"

@interface ChatViewController ()

@property (weak, nonatomic) IBOutlet UITextField *chatTextField;

@end

@implementation ChatViewController {}

- (void) viewDidLoad {
    [super viewDidLoad];
    [self subscribeMsg];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void) sendMsg {
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://1.255.56.109:8080/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    NSString *textMessage = [NSString stringWithFormat:@"%@", _chatTextField.text];
    NSDictionary *msgDict = @{@"content": textMessage};
    
    [client connectWithLogin:@"yongjai@gmail.com"
                    passcode:@"1234"
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               NSData *data = [NSJSONSerialization dataWithJSONObject:msgDict options:0 error:&error];
               NSString *body =[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
               [client sendTo:@"/app/hello" body:body];
               }];
}

- (void) subscribeMsg {
    NSURL *webSocketURL = [NSURL URLWithString:@"ws://1.255.56.109:8080/websockethandler/websocket"];
    STOMPClient *client = [[STOMPClient alloc] initWithURL:webSocketURL webSocketHeaders:nil useHeartbeat:NO];
    
    [client connectWithLogin:@"yongjai@gmail.com"
                    passcode:@"1234"
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               [client subscribeTo:@"/topic/roomId" messageHandler:^(STOMPMessage *message){
                   NSLog(@"got message %@", message.body);
               }];
           }];
}


- (IBAction)clickedSendChat:(id)sender {
    [self sendMsg];
    [self subscribeMsg];
}

@end
