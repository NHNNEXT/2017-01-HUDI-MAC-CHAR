//
//  LobbyViewController.m
//  HUDI-Mafia
//
//  Created by YongJai on 2017. 4. 14..
//  Copyright © 2017년 YongJai. All rights reserved.
//

#import "LobbyViewController.h"
#import "RoomListTableViewCell.h"
#import "ChatViewController.h"

@interface LobbyViewController () <UITableViewDataSource, UITableViewDelegate>
@end

@implementation LobbyViewController


- (void)viewDidLoad {
    [super viewDidLoad];
    [self getRoomList];
    _createRoomView.hidden = YES;
    _userNickName.text = _gotNickName;
}


- (void)viewWillAppear:(BOOL)animated {
    [_roomList reloadData];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


- (void) getRoomList {
    NSError *error;
    NSString *url = [NSString stringWithFormat: @"http://211.249.60.54:80/api/lobby"];
    NSData *data = [NSData dataWithContentsOfURL: [NSURL URLWithString:url]];
    
    _roomData = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
    _roomTitle = [[_roomData valueForKey:@"rooms"]valueForKey:@"title"];
    _roomNum = [[_roomData valueForKey:@"rooms"]valueForKey:@"id"];
    _userCount = [[_roomData valueForKey:@"rooms"]valueForKey:@"userCount"];
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [_roomNum count];
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [self enterRoom];
    if ([[_enterDataDictionary valueForKey:@"status"] isEqualToString:@"Ok"]) {
        [self performSegueWithIdentifier:@"chatSegue" sender:self];
    }
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *cellIdenfier =@"Cell";
    RoomListTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdenfier forIndexPath:indexPath];
    
    cell.backgroundView =  [[UIImageView alloc] initWithImage:[ [UIImage imageNamed:@"list_plate"] stretchableImageWithLeftCapWidth:0.0 topCapHeight:5.0] ];
    cell.selectedBackgroundView =  [[UIImageView alloc] initWithImage:[ [UIImage imageNamed:@"list_plate"] stretchableImageWithLeftCapWidth:0.0 topCapHeight:5.0] ];
    
    if (cell == nil) {
        cell = [[RoomListTableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdenfier];
    }
    
    if([_roomNum count] > 0 && [_roomNum count] > indexPath.row) {
        cell.roomTitle.text = [_roomTitle objectAtIndex:indexPath.row];
        cell.roomId.text = [NSString stringWithFormat:@"NO.%@",[_roomNum objectAtIndex:indexPath.row]];
        cell.userCount.text = [NSString stringWithFormat:@"%@ / 8",[_userCount objectAtIndex:indexPath.row]];
    }
    
    return  cell;
}


- (IBAction)clickedLogOut:(id)sender {
    [self performSegueWithIdentifier:@"logout" sender:self];
}


- (IBAction)clickedRefresh:(id)sender {
    [self getRoomList];
    [self viewWillAppear:YES];
}


- (IBAction)clickedPopUpRoomCreateView:(id)sender {
    _createRoomView.hidden = NO;
}


- (IBAction)clickedRoomCreateBtn:(id)sender {
    NSString *title = _createRoomTitleTextField.text;
    NSString *URLString = @"http://211.249.60.54:80/api/room";
    NSURL *URL = [NSURL URLWithString:URLString];
    NSMutableURLRequest *createRoomRequest = [NSMutableURLRequest requestWithURL:URL];
    [createRoomRequest setValue:@"application/json" forHTTPHeaderField: @"content-Type"];
    [createRoomRequest setHTTPMethod:@"POST"];
    
    NSDictionary *createRoomData = @{
                                @"title" : title
                                };
    
    [createRoomRequest setHTTPBody:[NSJSONSerialization dataWithJSONObject:createRoomData options:kNilOptions error:NULL]];
    NSHTTPURLResponse * createRoomResponse;
    NSError * createRoomError;
    NSData * createRoomResultData = [NSURLConnection sendSynchronousRequest:createRoomRequest returningResponse:&createRoomResponse error:&createRoomError];

    
    NSMutableArray *data = [NSJSONSerialization JSONObjectWithData:createRoomResultData options:NSJSONReadingMutableContainers error:nil];
    _createdRoomId = [data valueForKey:@"roomId"];
    [self enterRoomByCreateRoom];
    [self performSegueWithIdentifier:@"chatSegue" sender:self];
}


- (IBAction)clickedRoomCreateCancelBtn:(id)sender {
    _createRoomView.hidden = YES;
}


- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {

    NSIndexPath *indexpath = [self.roomListTable indexPathForSelectedRow];
    NSMutableArray *selectedTitle = [_roomTitle objectAtIndex:indexpath.row];
    NSMutableArray *selectedId = [_roomNum objectAtIndex:indexpath.row];
    NSMutableArray *selectedUserInfo = [[[_roomData valueForKey:@"rooms" ]valueForKeyPath:@"users"] objectAtIndex:indexpath.row];

    [segue.destinationViewController performSelector:@selector(setRoomTitle:)withObject:selectedTitle];
    [segue.destinationViewController performSelector:@selector(setRoomNum:)withObject:selectedId];
    [segue.destinationViewController performSelector:@selector(setUserInfo:)withObject:selectedUserInfo];
}

- (void)createRoomPrepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
}


- (void) enterRoom {
    NSIndexPath *indexpath = [self.roomListTable indexPathForSelectedRow];
    NSString *selectedNum = [_roomNum objectAtIndex:indexpath.row];
    NSString *URLString = [NSString stringWithFormat:@"http://211.249.60.54:80/api/room/%@", selectedNum];
    
    NSURL *URL = [NSURL URLWithString:URLString];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    
    NSHTTPURLResponse *response;
    NSError *error;
    
    NSData *resultData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    
    _enterDataDictionary = [NSJSONSerialization JSONObjectWithData:resultData options:NSJSONReadingMutableContainers error:nil];
    NSLog(@"%@", _enterDataDictionary);
}


- (void) enterRoomByCreateRoom {
    NSString *URLString = [NSString stringWithFormat:@"http://211.249.60.54:80/api/room/%@", _createdRoomId];
    NSURL *URL = [NSURL URLWithString:URLString];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    
    NSHTTPURLResponse *response;
    NSError *error;
    NSData *resultData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
}



@end
